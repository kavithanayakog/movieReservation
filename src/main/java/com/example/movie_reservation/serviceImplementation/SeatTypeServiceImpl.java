package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.SeatType;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.SeatTypeRepository;
import com.example.movie_reservation.service.SeatTypeService;
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
    public SeatType createSeatType(SeatType seatType) {

        if(seatType.getSeatTypeName() == null || seatType.getSeatTypeName().trim().isEmpty()){
            throw new RuntimeException("Seat type name cannot be null or empty");
        }
        if(seatType.getAmount() == null || seatType.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Amount must be greater than zero");
        }
        if (seatTypeRepository.existsBySeatTypeNameIgnoreCase(
                seatType.getSeatTypeName())) {
            throw new RuntimeException("Seat type name already exists");
        }
        seatType.setSeatTypeName(seatType.getSeatTypeName().toUpperCase());


        return seatTypeRepository.save(seatType);

    }

    @Override
    public SeatType getSeatTypeById(Long SeatTypeId) {

        return seatTypeRepository.findById(SeatTypeId)
                .orElseThrow(() -> new RuntimeException("SeatType not found with id: " + SeatTypeId));
    }

    @Override
    public SeatType updateSeatType(Long seatTypeId, SeatType seatType) {

        SeatType updatedSeatType = getSeatTypeById(seatTypeId);

        if(seatType.getSeatTypeName() == null || seatType.getSeatTypeName().trim().isEmpty()){
            throw new RuntimeException("Seat type name cannot be null or empty");
        }

        if(seatType.getAmount() == null || seatType.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Amount must be a positive value");
        }

        if (!updatedSeatType.getSeatTypeName()
                .equalsIgnoreCase(seatType.getSeatTypeName())
                && seatTypeRepository.existsBySeatTypeNameIgnoreCase(
                seatType.getSeatTypeName())) {
            throw new RuntimeException("Seat type name already exists");
        }


        updatedSeatType.setSeatTypeName(seatType.getSeatTypeName().toUpperCase());
        updatedSeatType.setAmount(seatType.getAmount());

        return seatTypeRepository.save(updatedSeatType);
    }

    @Override
    public void deleteSeatType(Long seatTypeId) {
            SeatType seatType = getSeatTypeById(seatTypeId);
            if(seatRepository.existsBySeatType_SeatTypeId(seatTypeId)){
                throw new RuntimeException("Cannot delete seat type assigned to seats");
            }
            seatTypeRepository.delete(seatType);
    }

    @Override
    public List<SeatType> getAllSeatTypes() {

        return seatTypeRepository.findAll();
    }
}
