package com.example.risus.dto.response;


import com.example.risus.entity.Dentist;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DentistResponse {

    private UUID id;
    private TranslatedField fullName;
    private TranslatedField specialty;
    private TranslatedField bio;
    private String photoUrl;
    private Integer displayOrder;

    public static DentistResponse from(Dentist d) {
        return DentistResponse.builder()
                .id(d.getId())
                .fullName(new TranslatedField(d.getFullNameUz(), d.getFullNameRu(), d.getFullNameEn()))
                .specialty(new TranslatedField(d.getSpecialtyUz(), d.getSpecialtyRu(), d.getSpecialtyEn()))
                .bio(new TranslatedField(d.getBioUz(), d.getBioRu(), d.getBioEn()))
                .photoUrl(d.getPhotoUrl())
                .displayOrder(d.getDisplayOrder())
                .build();
    }
}