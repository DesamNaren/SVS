package com.cgg.streetvendor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;

import java.util.List;


/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
//

@Dao
public interface SVSSyncKYCDao {

    @Query("SELECT religionId from religion_info where religionName LIKE :religion")
    LiveData<String> getReligionId(String religion);

    @Query("SELECT religionId from religion_info where religionNameTel LIKE :religion")
    LiveData<String> getTelReligionId(String religion);


    @Query("SELECT relationId from relation_info where relationName LIKE :relation")
    LiveData<String> getRelationId(String relation);

    @Query("SELECT relationId from relation_info where relationNameTel LIKE :relation")
    LiveData<String> getTelRelationId(String relation);

    @Query("SELECT casteId from caste_info where caste LIKE :caste")
    LiveData<String> getCasteId(String caste);

    @Query("SELECT casteId from caste_info where casteTel LIKE :caste")
    LiveData<String> getTelCasteId(String caste);

    @Query("SELECT genderCode from gender_info where genderName LIKE :gender")
    LiveData<String> getGenderCode(String gender);


    @Query("SELECT genderCode from gender_info where genderNameTel LIKE :gender")
    LiveData<String> getTelGenderCode(String gender);

    @Query("SELECT genderId from gender_info where genderName LIKE :gender")
    LiveData<String> getGenderID(String gender);

    @Query("SELECT genderId from gender_info where genderNameTel LIKE :gender")
    LiveData<String> getTelGenderID(String gender);


    @Query("SELECT pwdId from pwd_info where pwdType LIKE :pwd")
    LiveData<String> getPWDId(String pwd);

    @Query("SELECT pwdId from pwd_info where pwdTypeTel LIKE :pwd")
    LiveData<String> getTelPWDId(String pwd);

    @Query("SELECT qualificationId from qualification_info where qualificationType LIKE :quaName")
    LiveData<String> getQualificationId(String quaName);

    @Query("SELECT qualificationId from qualification_info where qualificationTypeTel LIKE :quaName")
    LiveData<String> getTelQualificationId(String quaName);


    @Insert
    void insertReligions(List<ReligionEntity> religionEntities);

    @Query("SELECT COUNT(*) FROM religion_info")
    int religionCount();

    @Query("DELETE FROM religion_info")
    void deleteReligion();


    @Query("DELETE FROM gender_info")
    void deleteGenders();

    @Insert
    void insertGenders(List<GenderEntity> genderEntities);

    @Query("SELECT COUNT(*) FROM gender_info")
    int genCount();

    @Query("DELETE FROM caste_info")
    void deleteCaste();

    @Insert
    void insertCaste(List<CasteEntity> wardEntities);

    @Query("SELECT COUNT(*) FROM caste_info")
    int casteCount();


    @Query("DELETE FROM pwd_info")
    void deletePWD();

    @Insert
    void insertPWD(List<PWDEntity> pwdEntities);

    @Query("SELECT COUNT(*) FROM pwd_info")
    int pwdCount();


    @Query("DELETE FROM qualification_info")
    void deleteQualification();

    @Insert
    void insertQualification(List<QualificationEntity> qualificationEntities);

    @Query("SELECT COUNT(*) FROM qualification_info")
    int qualificationCount();


    @Query("DELETE FROM relation_info")
    void deleteRelation();

    @Insert
    void insertRelation(List<RelationEntity> relationEntities);

    @Query("SELECT COUNT(*) FROM relation_info")
    int relationCount();


    @Query("SELECT * from gender_info")
    LiveData<List<GenderEntity>> getAllGenders();

    @Query("SELECT * from caste_info")
    LiveData<List<CasteEntity>> getAllCaste();

    @Query("SELECT * from pwd_info")
    LiveData<List<PWDEntity>> getAllPWDs();

    @Query("SELECT * from qualification_info")
    LiveData<List<QualificationEntity>> getAllQualification();


    @Query("SELECT * from relation_info")
    LiveData<List<RelationEntity>> getAllRelations();


    @Query("SELECT * from religion_info")
    LiveData<List<ReligionEntity>> getAllReligions();

}
