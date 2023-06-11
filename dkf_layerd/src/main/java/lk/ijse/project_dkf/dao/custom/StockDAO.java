package lk.ijse.project_dkf.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.project_dkf.dao.CrudDAO;
import lk.ijse.project_dkf.dto.ShipmentDTO;
import lk.ijse.project_dkf.dto.StockDTO;

import java.sql.SQLException;

public interface StockDAO extends CrudDAO<StockDTO, String> {

    boolean update(ObservableList<ShipmentDTO> shipmentDTOS) throws SQLException;

    int searchAvailability(String id, String size) throws SQLException;
}
