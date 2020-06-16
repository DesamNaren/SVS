package com.cgg.streetvendor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;

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
public interface SVSSyncPlacesDao {

    @Query("DELETE FROM State_Info")
    void deleteStates();

    @Insert
    void insertState(List<StateEntity> stateEntities);

    @Query("SELECT COUNT(*) FROM State_Info")
    int stateCount();

    @Query("SELECT * from state_info")
    LiveData<List<StateEntity>> getAllStates();

    @Query("SELECT * from district_info")
    LiveData<List<DistrictEntity>> getAllDistricts();

    @Query("SELECT * from mandal_info")
    LiveData<List<MandalEntity>> getAllMandals();

    @Query("SELECT * from village_info")
    LiveData<List<VillageEntity>> getAllVillages();

    @Query("DELETE FROM district_info")
    void deleteDistricts();

    @Insert
    void insertDistricts(List<DistrictEntity> districtEntities);

    @Query("SELECT COUNT(*) FROM district_info")
    int distCount();


    @Query("DELETE FROM mandal_info")
    void deleteMandals();

    @Insert
    void insertMandals(List<MandalEntity> mandalEntities);

    @Query("SELECT COUNT(*) FROM mandal_info")
    int manCount();

    @Query("DELETE FROM village_info")
    void deleteVillages();

    @Insert
    void insertVillages(List<VillageEntity> villageEntities);

    @Query("SELECT COUNT(*) FROM village_info")
    int vilCount();

    @Query("SELECT stateId from state_info where stateType LIKE :stateName")
    LiveData<String> getStateId(String stateName);

    @Query("SELECT stateId from state_info where stateTypeTel LIKE :stateName")
    LiveData<String> getTelStateId(String stateName);

    @Query("SELECT * from mandal_info where districtId LIKE :distID")
    LiveData<List<MandalEntity>> getDistrictMandals(String distID);

    @Query("SELECT * from village_info where districtId LIKE :distID AND mandalId LIKE :manID")
    LiveData<List<VillageEntity>> getMandalVillages(String distID, String manID);

    @Query("SELECT districtId from district_info where districtName LIKE :distName")
    LiveData<String> getDistId(String distName);

    @Query("SELECT districtId from district_info where districtNameTel LIKE :distName")
    LiveData<String> getTelDistId(String distName);


    @Query("SELECT mandalId from mandal_info " +
            "where mandalName LIKE :manName AND districtId LIKE :distID")
    LiveData<String> getMandalId(String manName, String distID);


    @Query("SELECT mandalId from mandal_info " +
            "where mandalNameTel LIKE :manName AND districtId LIKE :distID")
    LiveData<String> getTelMandalId(String manName, String distID);


    @Query("SELECT districtId from village_info " +
            "where villageName LIKE :vilName AND districtId LIKE :distId AND mandalId LIKE :manId")
    LiveData<String> getVillageId(String vilName, String distId, String manId);

    @Query("SELECT districtId from village_info " +
            "where villageNameTel LIKE :vilName AND districtId LIKE :distId AND mandalId LIKE :manId")
    LiveData<String> getTelVillageId(String vilName, String distId, String manId);

}
