import React, { Component } from "react";
import InvoiceSearch from "./InvoiceSearch";
import CreateRefundInvoice from "./CreateRefundInvoice";
import BatchList from "../BatchList";
import { isEmptyObject } from "../../utils/object";
import "../../style/css/refundScreen.css";

export default class InvoiceContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      invoice: {},
      message: "",
      createNew: false,
    };
  }

  setCreateNew = () => {
    this.setState({
      createNew: !this.state.createNew,
    });
  };

  setInvoice = (invoice) => {
    this.setState({
      invoice: invoice,
      message: "",
    });
  };

  setMessage = (message) => {
    this.setState({ message: message });
  };

  handleCreateButton = () => {
    if (!isEmptyObject(this.state.invoice)) {
      this.setCreateNew();
    }
  };

  render() {
    let tempPrice = "";
    let time = "";
    if (!isEmptyObject(this.state.invoice)) {
      tempPrice =
        this.state.invoice.totalPrice +
        this.state.invoice.totalPrice * this.state.invoice.discount;
      time = new Date(this.state.invoice.date).toLocaleString().split(",")[0];
    }
    return (
      <>
        {this.state.createNew ? (
          <CreateRefundInvoice
            cancel={this.setCreateNew}
            detail={this.state.invoice.invoiceDetail}
          ></CreateRefundInvoice>
        ) : (
          <div className="refund-invoice-container">
            <InvoiceSearch
              setInvoice={this.setInvoice}
              setMessage={this.setMessage}
            />
            <div className="invoice-info">
              <div className="center-header-18">Thông tin hóa đơn</div>
              <div id="invoice-info__info">
                <div className="invoice-info__detail">
                  <span>Mã hóa đơn:</span>
                  <span>{this.state.invoice.id}</span>
                </div>
                <div className="invoice-info__detail">
                  <span>Ngày tạo:</span>
                  <span>{time}</span>
                </div>
              </div>
            </div>
            <BatchList
              list={this.state.invoice.invoiceDetail}
              isDeleteAllowed={false}
            />
            <div className="total-money" style={{ marginBottom: "30px" }}>
              <table className="total-money-table center">
                <tbody>
                  <tr>
                    <td className="left-column">Tổng tiền hàng:</td>
                    <td className="right-column">{tempPrice}</td>
                  </tr>
                  <tr>
                    <td className="left-column">Giảm giá:</td>
                    <td className="right-column">
                      {this.state.invoice.discount}
                    </td>
                  </tr>
                  <tr>
                    <td className="left-column">Thanh toán:</td>
                    <td className="right-column">
                      {this.state.invoice.totalPrice}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div style={{ width: "100%", textAlign: "center" }}>
              {this.state.message === "" ? (
                <>
                  <button
                    className="btn-type-2 btn-confirm"
                    onClick={this.handleCreateButton}
                    style={{ width: "300px", margin: "0 auto" }}
                  >
                    Tạo hóa đơn trả
                  </button>
                </>
              ) : (
                <p id="refundMessage">{this.state.message}</p>
              )}
            </div>
          </div>
        )}
      </>
    );
  }
}
