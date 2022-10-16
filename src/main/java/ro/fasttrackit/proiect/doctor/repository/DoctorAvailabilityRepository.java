package ro.fasttrackit.proiect.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.proiect.doctor.entity.DoctorAvailability;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
}
