package entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "students")
public class Student extends Person implements Serializable {

	private static final long serialVersionUID = -2351793401506633645L;
	@Column(name = "enrollment_year")
	private int enrollmentYear;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Borrow> borrows;

	public Student(int id, String firstName, String lastName, String email, int enrollmentYear) {
		super(id, firstName, lastName, email);
		this.enrollmentYear = enrollmentYear;
	}

	@Override
	public String toString() {
		return "Student [enrollmentYear=" + enrollmentYear + ", toString()=" + super.toString() + "]";
	}

}
