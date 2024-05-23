package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

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
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="books")
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	private String title;
	private String isbn;
	@Column(name = "publication_date")
	private LocalDate publicationDate;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private Set<BorrowDetail> borrowDetails;

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", isbn=" + isbn + ", publicationDate=" + publicationDate + "]";
	}

	public Book(int id, String title, String isbn, LocalDate publicationDate) {
		super();
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.publicationDate = publicationDate;
	}
	
	
}
