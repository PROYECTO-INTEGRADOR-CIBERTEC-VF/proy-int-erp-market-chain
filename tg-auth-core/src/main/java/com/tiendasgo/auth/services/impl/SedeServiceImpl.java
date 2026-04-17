package com.tiendasgo.auth.services.impl;

import com.tiendasgo.auth.dto.request.SedeRequest;
import com.tiendasgo.auth.dto.response.SedeResponse;
import com.tiendasgo.auth.domain.entity.Sede;
import com.tiendasgo.auth.domain.repository.SedeRepository;
import com.tiendasgo.auth.exceptions.CustomException;
import com.tiendasgo.auth.services.ISedeService;
import com.tiendasgo.auth.utils.SedeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements ISedeService {

    private final SedeRepository sedeRepository;

    @Override
    public List<SedeResponse> listarSedes() {
        return sedeRepository.findAll().stream()
            .map(SedeMapper::toResponse)
            .toList();
    }

    @Override
    public SedeResponse obtenerSedePorId(Integer idSede) {
        Sede sede = sedeRepository.findById(idSede)
            .orElseThrow(() -> new CustomException("Sede no encontrada", HttpStatus.NOT_FOUND));
        return SedeMapper.toResponse(sede);
    }

    @Override
    public SedeResponse crearSede(SedeRequest request) {
        Sede sede = new Sede();
        aplicarDatosRequest(sede, request);

        return SedeMapper.toResponse(sedeRepository.save(sede));
    }

    @Override
    public SedeResponse actualizarSede(Integer idSede, SedeRequest request) {
        Sede sede = sedeRepository.findById(idSede)
            .orElseThrow(() -> new CustomException("Sede no encontrada", HttpStatus.NOT_FOUND));

        aplicarDatosRequest(sede, request);

        return SedeMapper.toResponse(sedeRepository.save(sede));
    }

    private void aplicarDatosRequest(Sede sede, SedeRequest request) {
        sede.setNombre(normalizeText(request.getNombre()));
        sede.setEmail(normalizeText(request.getEmail()));
        sede.setGerenteNombre(normalizeText(request.getGerenteNombre()));
        sede.setDireccion(normalizeText(request.getDireccion()));
        sede.setUbigeo(normalizeText(request.getUbigeo()));
        sede.setTelefono(normalizeText(request.getTelefono()));
        sede.setEsAlmacenCentral(request.getEsAlmacenCentral() != null
            ? request.getEsAlmacenCentral()
            : Boolean.FALSE);
        sede.setEstado(request.getEstado() != null ? request.getEstado() : Boolean.TRUE);
        sede.setHorarioConfig(normalizeText(request.getHorarioConfig()));
    }

    private String normalizeText(String value) {
        return value == null ? null : value.trim();
    }

    @Override
    public void eliminarSede(Integer idSede) {
        if (!sedeRepository.existsById(idSede)) {
            throw new CustomException("Sede no encontrada", HttpStatus.NOT_FOUND);
        }
        sedeRepository.deleteById(idSede);
    }
}
