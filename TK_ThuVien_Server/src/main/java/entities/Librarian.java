package entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "librarians")
public class Librarian extends Person implements Serializable {

	private static final long serialVersionUID = 8202400538176788650L;

	private String description;
	
	@OneToMany(mappedBy = "librarian", cascade = CascadeType.ALL)
	private Set<Borrow> borrows;

	@Override
	public String toString() {
		return "Librarian [description=" + description + ", toString()=" + super.toString() + "]";
	}

	public Librarian(int id, String firstName, String lastName, String email, String description) {
		super(id, firstName, lastName, email);
		this.description = description;
	}
	
}
