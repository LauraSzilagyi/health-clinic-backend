package ro.fasttrackit.proiect.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.entity.Person;
import ro.fasttrackit.proiect.doctor.enums.Department;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findAllByDepartment(Department department);

    Optional<Person> findByEmailAndIsAdmin(String email, Boolean isAdmin);
}
