package cookcloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBER")
public class Member implements Serializable{

	private static final long serialVersionUID = 958674608281975L;

	@Id
	@Column(name="MEM_ID", columnDefinition = "VARCHAR2(20)")
	@NotBlank(message = "아이디는 필수 항목입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력하세요.")
	private String memId;

	@Column(name="MEM_PASSWORD",columnDefinition = "VARCHAR2(255)", nullable = false)
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
	private String memPassword;

	@Column(name="MEM_NAME", columnDefinition = "NVARCHAR2(50)", nullable = false)
	@NotBlank(message = "이름은 필수 항목입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력하세요.")
	private String memName;

	@Column(name="MEM_NICKNAME", unique = true, columnDefinition = "NVARCHAR2(10)", nullable = false)
	@NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(min = 4, max = 8, message = "닉네임은 4자 이상 8자 이하로 입력하세요.")
	private String memNickname;

	@Column(name="MEM_EMAIL", columnDefinition = "VARCHAR2(50)", nullable = false)
	private String memEmail;

	@Column(name="MEM_PHONE", columnDefinition = "VARCHAR2(20)", nullable = false)
	private String memPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MEM_INSERT_AT", nullable = false)
	private LocalDateTime memInsertAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MEM_DELETE_AT")
	private LocalDateTime memDeleteAt;

	@Column(name="MEM_REPORT_COUNT")
	private Long memReportCount;

	@Column(name="ROLE_CODE", nullable = false)
	private Long roleCode;
	
	@Column(name="MEM_STATUS_CODE", nullable = false)
	private Long memStatusCode;

	@OneToMany(mappedBy = "member")
	private List<Recipe> recipeList;
	
	@OneToMany(mappedBy = "follower")
	private List<Follows> followsList;
	
	@OneToMany(mappedBy = "following")
	private List<Follows> followingList;
	
	@OneToMany(mappedBy = "member")
	private List<Likes> likesList;
	
	@OneToMany(mappedBy = "member")
	private List<Review> reviewList;
	
	@OneToMany(mappedBy = "member")
	private List<Message> messageList;
	
	@OneToMany(mappedBy = "member")
	private List<MemberAllergyFood> memberAllergyFoodList;
	
	@OneToMany(mappedBy = "member")
	private List<Inquiry> inquiryList;
	
	@OneToMany(mappedBy = "member")
	private List<Report> reportList;
}