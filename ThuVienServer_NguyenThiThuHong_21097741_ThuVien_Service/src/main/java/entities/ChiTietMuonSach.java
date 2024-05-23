package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChiTietMuonSach implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2720314277921713416L;
	@Id
	@Column(name="NgayMuon", columnDefinition = "datetime")
	private Date ngayMuon;
	@Column(name="NgayTra",columnDefinition = "datetime")
	private Date ngayTra;
	@Column(name="TienCoc")
	private long tienCoc;
	
	@ManyToOne
	@Id
	@JoinColumn(name = "MaSach")
	private Sach sach;

	@ManyToOne
	@Id
	@JoinColumn(name = "MaDG")
	private DocGia docGia;

	public ChiTietMuonSach(Date ngayMuon, Date ngayTra, long tienCoc, Sach sach, DocGia docGia) {
		super();
		this.ngayMuon = ngayMuon;
		this.ngayTra = ngayTra;
		this.tienCoc = tienCoc;
		this.sach = sach;
		this.docGia = docGia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(docGia, sach);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietMuonSach other = (ChiTietMuonSach) obj;
		return Objects.equals(docGia, other.docGia) && Objects.equals(sach, other.sach);
	}
	
	
	
 }
