package lk.ijse.project_dkf.dao.custom.impl;

import lk.ijse.project_dkf.dao.custom.BuyerDAO;
import lk.ijse.project_dkf.dto.BuyerDTO;
import lk.ijse.project_dkf.entity.Buyer;
import lk.ijse.project_dkf.tm.BuyerTM;
import lk.ijse.project_dkf.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAOImpl implements BuyerDAO {
    public boolean add(Buyer entity) throws SQLException {
        String sql = "INSERT INTO Buyer (BuyerID,BuyerName,BuyerCN,BuyerAddress ) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                entity.getBuyerID(),
                entity.getBuyerName(),
                entity.getBuyerCN(),
                entity.getBuyerAddress()
        );
    }

    public List<Buyer> getAll() throws SQLException {
        ArrayList<Buyer> allBuyers = new ArrayList<>();
        String sql = "SELECT * FROM Buyer";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            allBuyers.add(new Buyer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return allBuyers;
    }

    @Override
    public List<Buyer> getAll(String s) throws SQLException {
        return null;
    }

    public BuyerDTO search(String id) throws SQLException {
        String sql = "SELECT * FROM Buyer WHERE BuyerID=?";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()) {
            return new BuyerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public boolean update(Buyer entity) throws SQLException {
        String sql = "UPDATE Buyer SET BuyerName = ?, BuyerCN = ?, BuyerAddress = ? WHERE BuyerID = ?";
        boolean result = CrudUtil.execute(sql, entity.getBuyerName(), entity.getBuyerCN(), entity.getBuyerAddress(), entity.getBuyerID());
        return result;
    }

    public boolean delete(Buyer entity) throws SQLException {
        String sql = "DELETE FROM Buyer WHERE BuyerID=?";
        boolean result = CrudUtil.execute(sql, entity.getBuyerID());
        return result;
    }

    public String generateNewID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT BuyerID FROM Buyer ORDER BY BuyerID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("B00-", "")) + 1;
            return String.format("B00-%03d", newCustomerId);
        } else {
            return "B00-001";
        }
    }

    public List<String> loadIds() throws SQLException {
        String sql = "SELECT BuyerID FROM Buyer";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public Buyer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Buyer WHERE BuyerID=?";
        ResultSet resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new Buyer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }
}
