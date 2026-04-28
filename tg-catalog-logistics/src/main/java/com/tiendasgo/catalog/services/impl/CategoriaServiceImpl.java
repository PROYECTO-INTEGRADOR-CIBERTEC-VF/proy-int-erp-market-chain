package com.tiendasgo.catalog.services.impl;

import com.tiendasgo.catalog.domain.entity.Categoria;
import com.tiendasgo.catalog.domain.repository.CategoriaRepository;
import com.tiendasgo.catalog.dto.request.CategoriaRequest;
import com.tiendasgo.catalog.dto.response.CategoriaResponse;
import com.tiendasgo.catalog.exceptions.DuplicateResourceException;
import com.tiendasgo.catalog.exceptions.CodigoMarcaExhaustedException;
import com.tiendasgo.catalog.exceptions.ActiveSubcategoriesException;
import com.tiendasgo.catalog.domain.repository.SubCategoriaRepository;
import com.tiendasgo.catalog.exceptions.ResourceNotFoundException;
import com.tiendasgo.catalog.services.ICategoriaService;
import com.tiendasgo.catalog.utils.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final SubCategoriaRepository subCategoriaRepository;
    private static final Random RANDOM = new Random();

    @Override
    public List<CategoriaResponse> listarTodos() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponse obtenerPorId(Integer id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
        return categoriaMapper.toResponse(c);
    }

    @Override
    @Transactional
    public CategoriaResponse crear(CategoriaRequest req) {
        if (categoriaRepository.existsByNombre(req.getNombre())) {
            throw new DuplicateResourceException("Ya existe una categoria con ese nombre: " + req.getNombre());
        }
        Categoria c = categoriaMapper.toEntity(req);
        c.setActivo(Boolean.TRUE);
        String prefijo = generatePrefijoCandidato(req.getNombre());
        c.setPrefijo(prefijo);
        Categoria saved = categoriaRepository.save(c);
        return categoriaMapper.toResponse(saved);
    }

    private String generatePrefijoCandidato(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío para generar prefijo");
        }
        String norm = Normalizer.normalize(nombre.trim().toUpperCase(), Normalizer.Form.NFD)
                .replaceAll("[^A-Z0-9]", "");
        if (norm.length() < 3) {
            norm = String.format("%-3s", norm).replace(' ', 'X');
        }
        String base = norm.substring(0, 3);
        if (!categoriaRepository.existsByPrefijo(base)) {
            return base;
        }
        // Intentar rotar el tercer caracter con los caracteres únicos restantes
        LinkedHashSet<Character> unique = new LinkedHashSet<>();
        for (char c : norm.toCharArray()) unique.add(c);
        Iterator<Character> it = unique.iterator();
        char c1 = it.hasNext() ? it.next() : 'X';
        char c2 = it.hasNext() ? it.next() : 'X';
        // Saltar los dos primeros
        if (it.hasNext()) it.next();
        while (it.hasNext()) {
            char c3 = it.next();
            String candidate = "" + c1 + c2 + c3;
            if (!categoriaRepository.existsByPrefijo(candidate)) {
                return candidate;
            }
        }
        // Probar con sufijo numérico
        for (int i = 1; i <= 99; i++) {
            String prefix1 = String.valueOf(c1);
            String candidate = prefix1 + String.format("%02d", i);
            if (!categoriaRepository.existsByPrefijo(candidate)) {
                return candidate;
            }
        }
        // Random alfanumérico
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 1000; i++) {
            String candidate = "" + chars.charAt(RANDOM.nextInt(chars.length()))
                    + chars.charAt(RANDOM.nextInt(chars.length()))
                    + chars.charAt(RANDOM.nextInt(chars.length()));
            if (!categoriaRepository.existsByPrefijo(candidate)) {
                return candidate;
            }
        }
        throw new CodigoMarcaExhaustedException("No se pudo generar un prefijo único para la categoría: " + nombre);
    }

    @Override
    @Transactional
    public CategoriaResponse actualizar(Integer id, CategoriaRequest req) {
        Categoria existing = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));

        if (!existing.getNombre().equalsIgnoreCase(req.getNombre()) && categoriaRepository.existsByNombre(req.getNombre())) {
            throw new DuplicateResourceException("Ya existe una categoria con ese nombre: " + req.getNombre());
        }

        boolean nombreCambio = !existing.getNombre().equalsIgnoreCase(req.getNombre());
        existing.setNombre(req.getNombre());
        if (nombreCambio) {
            String nuevoPrefijo = generatePrefijoCandidato(req.getNombre());
            existing.setPrefijo(nuevoPrefijo);
        }

        Categoria updated = categoriaRepository.save(existing);
        return categoriaMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void eliminar(Integer id, boolean force) {
        Categoria existing = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));

        // buscar subcategorías activas
        boolean hasActive = subCategoriaRepository.findByCategoriaId(id).stream()
                .anyMatch(sc -> Boolean.TRUE.equals(sc.getActivo()));

        if (hasActive && !force) {
            throw new ActiveSubcategoriesException("Existen subcategorías activas. Use force=true para forzar la desactivación.");
        }

        // inactivar subcategorías si force
        if (hasActive && force) {
            subCategoriaRepository.findByCategoriaId(id).forEach(sc -> {
                sc.setActivo(Boolean.FALSE);
                subCategoriaRepository.save(sc);
            });
        }

        existing.setActivo(Boolean.FALSE);
        categoriaRepository.save(existing);
    }
}

