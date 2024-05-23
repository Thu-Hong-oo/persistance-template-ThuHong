package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name= "borrows")
public class Borrow implements Serializable{

	private static final long serialVersionUID = -1452566269069155842L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "borrow_id")
	private int id;
	@Column(name = "borrow_date")
	private LocalDate borrowDate;
	@Column(name = "return_date")
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name= "student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name= "librarian_id")
	private Librarian librarian;
	
	@OneToMany(mappedBy = "borrow",cascade = CascadeType.ALL)
	private Set<BorrowDetail> borrowDetails;

	public Borrow(int id, LocalDate borrowDate, LocalDate returnDate, Student student) {
		super();
		this.id = id;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.student = student;
	}
	
	
}
