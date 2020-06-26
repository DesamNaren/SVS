package com.cgg.streetvendor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;

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
public interface SVSSyncVendingDao {

    @Query("SELECT businessId from business_info where businessName LIKE :business")
    LiveData<String> getBusinessId(String business);

    @Query("SELECT businessId from business_info where businessNameTel LIKE :business")
    LiveData<String> getTelBusinessId(String business);

    @Query("SELECT vendingId from vending_type_info where vendingType LIKE :vType")
    LiveData<String> getVenTypeId(String vType);

    @Query("SELECT vendingId from vending_type_info where vendingTypeTel LIKE :vType")
    LiveData<String> getTelVenTypeId(String vType);

    @Query("SELECT vendingAreaId from vending_address_info where vendingAreaName LIKE :vAddress AND ulbId LIKE :ULBId AND districtId LIKE :distId")
    LiveData<String> getVenAddressId(String vAddress,String ULBId,String distId);

    @Query("SELECT vendingAreaId from vending_address_info where vendingAreaNameTel LIKE :vAddress AND ulbId LIKE :ULBId AND districtId LIKE :distId")
    LiveData<String> getTelVenAddressId(String vAddress, String ULBId,String distId);

    @Query("DELETE FROM business_info")
    void deleteBusiness();

    @Insert
    void insertBusiness(List<BusinessEntity> businessEntities);

    @Query("SELECT COUNT(*) FROM business_info")
    int businessCount();

    @Query("DELETE FROM vending_type_info")
    void deleteVenType();

    @Insert
    void insertVenType(List<VendingTypeEntity> vendingTypeEntities);

    @Query("SELECT COUNT(*) FROM vending_type_info")
    int venTypeCount();


    @Query("DELETE FROM vending_address_info")
    void deleteVenAddress();

    @Insert
    void insertVenAddress(List<VendingAddressEntity> vendingAddressEntities);

    @Query("SELECT COUNT(*) FROM vending_address_info")
    int venAddressCount();


    @Query("SELECT * from business_info")
    LiveData<List<BusinessEntity>> getAllBusiness();

    @Query("SELECT * from vending_type_info")
    LiveData<List<VendingTypeEntity>> getAllVenTypes();

    @Query("SELECT * from vending_address_info")
    LiveData<List<VendingAddressEntity>> getAllVenAddress();

    @Query("SELECT * from vending_address_info where ulbId LIKE :ULBId AND districtId LIKE :distId")
    LiveData<List<VendingAddressEntity>> getAllVenAddress(String ULBId,String distId);
}
