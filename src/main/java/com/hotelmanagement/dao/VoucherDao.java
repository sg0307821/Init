package com.hotelmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hotelmanagement.model.Voucher;
import com.hotelmanagement.utils.JDBCUtils;

public class VoucherDao {

    public Voucher findVoucherByCode(String code) {
        Voucher voucher = null;
        String sql = "SELECT * FROM vouchers WHERE code = ?";

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                voucher = new Voucher();
                voucher.setId(resultSet.getInt("id"));
                voucher.setCode(resultSet.getString("code"));
                voucher.setDescription(resultSet.getString("description"));
                voucher.setDiscountType(resultSet.getString("discount_type"));
                voucher.setDiscountValue(resultSet.getDouble("discount_value"));
                voucher.setUsageLimit(resultSet.getInt("usage_limit"));
                voucher.setValidityStart(resultSet.getString("validity_start"));
                voucher.setValidityEnd(resultSet.getString("validity_end"));
                voucher.setMinPurchase(resultSet.getDouble("min_purchase"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucher;
    }
    
}
