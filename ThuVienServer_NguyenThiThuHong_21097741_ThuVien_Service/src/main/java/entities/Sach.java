package entities;

import java.io.Serializable;
import java.time.Year;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Sach implements Serializable {

	private static final long serialVersionUID = 1005616690362658048L;

	@Id
	@Column(name = "MaSach", columnDefinition = "varchar(13)")
	private String maSach;
	@Column(name = "TuaSach", columnDefinition = "nvarchar(100)")
	private String tuaSach;
	@Column(name = "TacGia", columnDefinition = "nvarchar(100)")
	private String tacGia;
	@Column(name = "NamXB")
	private int namXB;
	@Column(name = "GiaBia")
	private long giaBia;

	@OneToMany(mappedBy = "sach")
	private Set<ChiTietMuonSach> chiTietMuonSachs;
	
	

	@Override
	public String toString() {
		return "Sach [maSach=" + maSach + ", tuaSach=" + tuaSach + ", tacGia=" + tacGia + ", namXB=" + namXB
				+ ", giaBia=" + giaBia + "]";
	}


	public Sach(String maSach, String tuaSach, String tacGia, int namXB, long giaBia) {
		super();
		this.maSach = maSach;
		this.tuaSach = tuaSach;
		this.tacGia = tacGia;
		this.namXB = namXB;
		this.giaBia = giaBia;
	}

}
