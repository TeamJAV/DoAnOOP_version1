import React, { Component } from "react";
import InvoiceSearch from "./InvoiceSearch";
import { Button } from "react-bootstrap";
import CreateRefundInvoice from "./CreateRefundInvoice";
import BatchList from "../BatchList";
import { isEmptyObject } from "../../utils/object";

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
    if (!isEmptyObject(this.state.invoice)) {
      tempPrice =
        this.state.invoice.totalPrice +
        this.state.invoice.totalPrice * this.state.invoice.discount;
    }
    return (
      <>
        {this.state.createNew ? (
          <CreateRefundInvoice
            cancel={this.setCreateNew}
            detail={this.state.invoice.invoiceDetail}
          ></CreateRefundInvoice>
        ) : (
          <>
            <InvoiceSearch
              setInvoice={this.setInvoice}
              setMessage={this.setMessage}
            />
            <div className="invoice-info">
              <div>Thông tin hóa đơn:</div>
              <span>Mã hóa đơn: {this.state.invoice.id}</span>
              <span>Ngày tạo: {this.state.invoice.date}</span>
            </div>
            <BatchList
              list={this.state.invoice.invoiceDetail}
              isDeleteAllowed={false}
            />
            <div className="total-money">
              <table className="total-money-table">
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
            {this.state.message === "" ? (
              <>
                <Button onClick={this.handleCreateButton}>
                  Tạo hóa đơn trả
                </Button>
              </>
            ) : (
              <div>
                <p>{this.state.message}</p>
              </div>
            )}
          </>
        )}
      </>
    );
  }
}
