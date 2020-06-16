package com.cgg.streetvendor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;

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
public interface SVSSyncULBDao {

    @Query("DELETE FROM ulb_info")
    void deleteULBs();

    @Insert
    void insertULBs(List<UlbEntity> ulbEntities);

    @Query("SELECT COUNT(*) FROM ulb_info")
    int ulbCount();

    @Query("DELETE FROM ward_info")
    void deleteWards();

    @Insert
    void insertWards(List<WardEntity> wardEntities);

    @Query("SELECT COUNT(*) FROM ward_info")
    int wardCount();

    @Query("SELECT * from ulb_info")
    LiveData<List<UlbEntity>> getAllULBs();

    @Query("SELECT * from ulb_info where ulbId LIKE :ulbId")
    LiveData<List<UlbEntity>> getLoggedULBs(String ulbId);

    @Query("SELECT * from ward_info")
    LiveData<List<WardEntity>> getAllWards();


    @Query("SELECT ulbId from ulb_info where ulbName LIKE :ulbName")
    LiveData<String> getULBId(String ulbName);

    @Query("SELECT ulbId from ulb_info where ulbNameTel LIKE :ulbName")
    LiveData<String> getTelULBId(String ulbName);

    @Query("SELECT wardId from ward_info where wardName LIKE :wardName AND ulbId LIKE :ulbId")
    LiveData<String> getWardId(String wardName, String ulbId);

    @Query("SELECT * from ward_info where ulbId LIKE :UlbID")
    LiveData<List<WardEntity>> getULBWards(String UlbID);

}
