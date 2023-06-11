package lk.ijse.project_dkf.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.project_dkf.bo.custom.OrderBO;
import lk.ijse.project_dkf.bo.custom.OrderRatioBO;
import lk.ijse.project_dkf.bo.custom.TrimCardBO;
import lk.ijse.project_dkf.controller.NewOrderFormController;
import lk.ijse.project_dkf.controller.OrderRatioController;
import lk.ijse.project_dkf.controller.TrimCardFormController;
import lk.ijse.project_dkf.dao.DAOFactory;
import lk.ijse.project_dkf.dao.custom.OrderRatioDAO;
import lk.ijse.project_dkf.dao.custom.OrdersDAO;
import lk.ijse.project_dkf.dao.custom.TrimCardDAO;
import lk.ijse.project_dkf.dao.custom.impl.TrimCardDAOImpl;
import lk.ijse.project_dkf.db.DBConnection;
import lk.ijse.project_dkf.tm.TrimCardTM;

import java.sql.Connection;
import java.sql.SQLException;

public class TrimCardBOImpl implements TrimCardBO {
    OrdersDAO ordersDAO= DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOType.ORDERS);
    OrderRatioDAO orderRatioDAO=DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOType.ORDER_RATIO);
    TrimCardDAO trimCardDAO=DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOType.TRIM_CARD);
    public boolean addTrimCard(ObservableList<TrimCardTM> trimCardObj, String orderId) throws SQLException {
        return trimCardDAO.addTrimCard(trimCardObj,orderId);
    }
    public String getNextTrimID() throws SQLException {
        return trimCardDAO.generateNewID();
    }
    public boolean placeOrder() throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isOrderSaved = ordersDAO.add(NewOrderFormController.orderDTO);
            if (isOrderSaved) {
                boolean isOrderRatioSaved = orderRatioDAO.addRatio(OrderRatioController.orderRatioTM, NewOrderFormController.orderDTO.getOrderId());
                if (isOrderRatioSaved) {
                    boolean isTrimCardSave = addTrimCard(TrimCardFormController.trimCardObj, NewOrderFormController.orderDTO.getOrderId());
                    if (isTrimCardSave) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }
}