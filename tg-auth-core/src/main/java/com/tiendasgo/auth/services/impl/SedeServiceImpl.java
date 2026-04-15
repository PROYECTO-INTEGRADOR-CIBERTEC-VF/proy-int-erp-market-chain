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
        sede.setNombre(request.getNombre());
        sede.setEmail(request.getEmail());
        sede.setGerenteNombre(request.getGerenteNombre());
        sede.setDireccion(request.getDireccion());
        sede.setUbigeo(request.getUbigeo());
        sede.setTelefono(request.getTelefono());
        sede.setEsAlmacenCentral(request.getEsAlmacenCentral());
        sede.setEstado(request.getEstado());
        sede.setHorarioConfig(request.getHorarioConfig());

        return SedeMapper.toResponse(sedeRepository.save(sede));
    }

    @Override
    public SedeResponse actualizarSede(Integer idSede, SedeRequest request) {
        Sede sede = sedeRepository.findById(idSede)
            .orElseThrow(() -> new CustomException("Sede no encontrada", HttpStatus.NOT_FOUND));

        sede.setNombre(request.getNombre());
        sede.setEmail(request.getEmail());
        sede.setGerenteNombre(request.getGerenteNombre());
        sede.setDireccion(request.getDireccion());
        sede.setUbigeo(request.getUbigeo());
        sede.setTelefono(request.getTelefono());
        sede.setEsAlmacenCentral(request.getEsAlmacenCentral());
        sede.setEstado(request.getEstado());
        sede.setHorarioConfig(request.getHorarioConfig());

        return SedeMapper.toResponse(sedeRepository.save(sede));
    }

    @Override
    public void eliminarSede(Integer idSede) {
        if (!sedeRepository.existsById(idSede)) {
            throw new CustomException("Sede no encontrada", HttpStatus.NOT_FOUND);
        }
        sedeRepository.deleteById(idSede);
    }
}
