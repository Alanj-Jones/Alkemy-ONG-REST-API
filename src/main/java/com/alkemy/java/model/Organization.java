package com.alkemy.java.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;

@Builder
@Entity
@SQLDelete(sql = "UPDATE organization SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El campo name es obligatorio")
    private String name;

    @NotNull(message = "El campo image es obligatoria")
    private String  image;
    
    private String address;

    private String phone;

    @Email(message = "Email debe ser un email válido")
    private String email;

    @NotNull(message = "El campo welcomeText es obligatorio")
    @Column(columnDefinition="TEXT")
    private String welcomeText;

    @Column(columnDefinition="TEXT")
    private String aboutUsText;

    @URL
    @Column()
    private String linkedinUrl;

    @URL
    @Column()
    private String facebookUrl;

    @URL
    @Column()
    private String instagramUrl;

    @CreationTimestamp
    private LocalDateTime creationDateTime;
 
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private boolean deleted;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizationId")
    private List<Slide> slides;






    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getAboutUsText() {
        return aboutUsText;
    }

    public void setAboutUsText(String aboutUsText) {
        this.aboutUsText = aboutUsText;
    }

    public LocalDateTime getCreateDateTime() {
        return creationDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.creationDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public Organization() {
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }


    public Organization(Long id, @NotNull(message = " El campo name es obligatorio") String name,
            @NotNull(message = "el campo image es obligatoria") String image, String address, String phone,
            @Email(message = "email debe ser un email válido") String email,
            @NotNull(message = "welcomeText es obligatorio") String welcomeText, String aboutUsText,
            LocalDateTime creationDateTime, LocalDateTime updateDateTime, boolean deleted, List<Slide> slides) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
        this.creationDateTime = creationDateTime;
        this.updateDateTime = updateDateTime;
        this.deleted = deleted;
        this.slides = slides;
    }


    public Organization(Long id, String name, String image, String address, String phone, String email, String welcomeText,
                        String aboutUsText, LocalDateTime creationDateTime, LocalDateTime updateDateTime, boolean deleted) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
        this.creationDateTime = creationDateTime;
        this.updateDateTime = updateDateTime;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Organization [aboutUsText=" + aboutUsText + ", address=" + address + ", creationDateTime="
                + creationDateTime + ", deleted=" + deleted + ", email=" + email + ", id=" + id + ", image=" + image
                + ", name=" + name + ", phone=" + phone + ", updateDateTime=" + updateDateTime + ", welcomeText="
                + welcomeText + "]";
    }

}
