package entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DocGia implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="MaDG", columnDefinition = "varchar(13)")
	private String maDG;
	@Column(name="HoTenDG",columnDefinition = "nvarchar(100)")
	private String hoTenDG;
	@Column(name="Email",columnDefinition = "varchar(100)")
	private String email;
	@Column(name="DienThoai",columnDefinition = "varchar(100)")
	private String dienThoai;
	
	@OneToMany(mappedBy = "docGia")
	private Set<ChiTietMuonSach> chiTietMuonSachs;
	
	public DocGia(String maDG, String hoTenDG, String email, String dienThoai) {
		super();
		this.maDG = maDG;
		this.hoTenDG = hoTenDG;
		this.email = email;
		this.dienThoai = dienThoai;
	}

	@Override
	public String toString() {
		return "DocGia [maDG=" + maDG + ", hoTenDG=" + hoTenDG + ", email=" + email + ", dienThoai=" + dienThoai + "]";
	}




	
	
}
