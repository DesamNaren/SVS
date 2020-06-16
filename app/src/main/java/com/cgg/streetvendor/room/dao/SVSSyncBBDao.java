package com.cgg.streetvendor.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;

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
public interface SVSSyncBBDao {


    @Query("DELETE FROM bank_info")
    void deleteBanks();

    @Insert
    void insertBanks(List<BankEntity> bankEntities);

    @Query("SELECT COUNT(*) FROM bank_info")
    int bankCount();

    @Query("DELETE FROM branch_info")
    void deleteBranches();

    @Insert
    void insertBranches(List<BranchEntity> branchEntities);

    @Query("SELECT COUNT(*) FROM branch_info")
    int branchCount();

    @Query("SELECT * from bank_info")
    LiveData<List<BankEntity>> getAllBanks();

    @Query("SELECT * from branch_info")
    LiveData<List<BranchEntity>> getAllBranches();

    @Query("SELECT bankId from bank_info where bankName LIKE :bankName")
    LiveData<String> getBankId(String bankName);

    @Query("SELECT bankId from bank_info where bankNameTel LIKE :bankName")
    LiveData<String> getTelBankId(String bankName);


    @Query("SELECT branchId from branch_info where branchName LIKE :branchName AND bankId LIKE :bankId")
    LiveData<String> getBranchId(String branchName, String bankId);

    @Query("SELECT branchId from branch_info where branchNameTel LIKE :branchName AND bankId LIKE :bankId")
    LiveData<String> getTelBranchId(String branchName, String bankId);

    @Query("SELECT * from branch_info where bankId LIKE :bankId")
    LiveData<List<BranchEntity>> getBankBranches(String bankId);

    @Query("SELECT ifscCode from branch_info where bankId LIKE :bankId AND branchId LIKE :branchId")
    LiveData<String> getIFSCCode(String bankId, String branchId);

}
