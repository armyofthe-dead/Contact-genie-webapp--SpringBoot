package com.spring.genie.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CG_Contact")

public class ContactDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String contactName;
	private String nickName;

	@Override
	public String toString() {
		return "ContactDetail [cid=" + cid + ", contactName=" + contactName + ", nickName=" + nickName + ", work="
				+ work + ", email=" + email + ", phoneNumber=" + phoneNumber + ", image=" + image + ", description="
				+ description + ", user=" + user + "]";
	}

	private String work;
	@Column(unique = true)
	private String email;
	private String phoneNumber;
	private String image;
	@Column(length = 1000)
	private String description;
	@ManyToOne
	@JoinColumn(name = "CG_id")
	private User user;

	public ContactDetail(String contactName, String nickName, String work, String email, String phoneNumber,
			String image, String description, User user) {
		super();
		this.contactName = contactName;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.image = image;
		this.description = description;
		this.user = user;
	}

	public ContactDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactDetail(int cid, String contactName, String nickName, String work, String email, String phoneNumber,
			String image, String description, User user) {
		super();
		this.cid = cid;
		this.contactName = contactName;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.image = image;
		this.description = description;
		this.user = user;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
