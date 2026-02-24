package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.SeatType;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.SeatTypeRepository;
import com.example.movie_reservation.service.SeatTypeService;
import com.example.movie_reservation.requestDTO.SeatTypeRequestDTO;
import com.example.movie_reservation.responseDTO.SeatTypeResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatTypeServiceImpl implements SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;
    private final SeatRepository seatRepository;

    @Override
    public SeatTypeResponseDTO createSeatType(SeatTypeRequestDTO seatType) throws ResourceNotFoundException {

        System.out.println("seatType.getSeatTypeName() "+seatType.getSeatTypeName());
        if(seatType.getSeatTypeName() == null || seatType.getSeatTypeName().trim().isEmpty()){
            throw new ResourceNotFoundException("Seat type name cannot be null or empty");
        }
        if(seatType.getAmount() == null || seatType.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new ResourceNotFoundException("Amount must be greater than zero");
        }
        if (seatTypeRepository.existsBySeatTypeNameIgnoreCase(
                seatType.getSeatTypeName())) {
            throw new ResourceNotFoundException("Seat type name already exists");
        }
        seatType.setSeatTypeName(seatType.getSeatTypeName().toUpperCase());

        SeatType seatTypeRequest = SeatType.builder()
                .seatTypeName(seatType.getSeatTypeName())
                .amount(seatType.getAmount())
                .createdDate(seatType.getCreatedDate())
                .updatedDate(seatType.getUpdatedDate())
                .build();

        return mapToResponse(seatTypeRepository.save(seatTypeRequest));

    }

    @Override
    public SeatType getSeatTypeById(Long SeatTypeId) {

        return seatTypeRepository.findById(SeatTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("SeatType not found with id: " + SeatTypeId));
    }

    @Override
    public SeatTypeResponseDTO updateSeatType(Long seatTypeId, SeatTypeRequestDTO seatType) {

        SeatType updatedSeatType = getSeatTypeById(seatTypeId);

        if(seatType.getSeatTypeName() == null || seatType.getSeatTypeName().trim().isEmpty()){
            throw new ResourceNotFoundException("Seat type name cannot be null or empty");
        }

        if(seatType.getAmount() == null || seatType.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new ResourceNotFoundException("Amount must be a positive value");
        }

        if (!updatedSeatType.getSeatTypeName()
                .equalsIgnoreCase(seatType.getSeatTypeName())
                && seatTypeRepository.existsBySeatTypeNameIgnoreCase(
                seatType.getSeatTypeName())) {
            throw new ResourceNotFoundException("Seat type name already exists");
        }

        updatedSeatType.setSeatTypeName(seatType.getSeatTypeName().toUpperCase());
        updatedSeatType.setAmount(seatType.getAmount());

        return mapToResponse(seatTypeRepository.save(updatedSeatType));
    }

    @Override
    public void deleteSeatType(Long seatTypeId) {
            SeatType seatType = getSeatTypeById(seatTypeId);
            if(seatRepository.existsBySeatType_SeatTypeId(seatTypeId)){
                throw new ResourceNotFoundException("Cannot delete seat type assigned to seats");
            }
            seatTypeRepository.delete(seatType);
    }

    @Override
    public List<SeatType> getAllSeatTypes() {

        return seatTypeRepository.findAll();
    }

    private SeatTypeResponseDTO mapToResponse(SeatType seatType) {
        return new SeatTypeResponseDTO(
                seatType.getSeatTypeId(),
                seatType.getSeatTypeName(),
                seatType.getAmount(),
                seatType.getCreatedDate(),
                seatType.getUpdatedDate()
        );
    }
}
