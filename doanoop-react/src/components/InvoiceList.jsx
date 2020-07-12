import React, { Component } from "react";
import { isArrayNull } from "../utils/array";
import convertToLocaleString from "../utils/time";
import DetailModal from "./statistic/DetailModal";

export default class InvoiceList extends Component {
  state = {
    invoice: [],
    modalKey: "",
    showModal: false,
  };
  componentDidMount() {
    this.setInvoice();
  }
  componentDidUpdate(prevProps) {
    if (
      prevProps.time !== this.props.time ||
      prevProps.type !== this.props.type
    ) {
      this.setInvoice();
    }
  }
  setInvoice = () => {
    fetch(
      `http://localhost:8081/statistic/trans?time=${this.props.time}&type=${this.props.type}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.setState({
          invoice: data,
        }, () => {console.log(data)});
      })
      .catch((err) => {
        console.log(err);
      });
  };
  handleClickDetail = (event) => {
    const key = event.currentTarget.parentNode.dataset.key;
    console.log(key);
    this.toggleDetailModal(key);
  };
  toggleDetailModal = (key) => {
    this.setState({
      modalKey: key,
      showModal: !this.state.showModal,
    });
  };
  renderInvoiceResult = () => {
    if (isArrayNull(this.state.invoice)) {
      return null;
    }
    let key = 0;
    let detail = this.state.invoice.map((result) => {
      switch (this.props.type) {
        case "import":
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{result.id}</td>
              <td>{result.importDate}</td>
              <td>{result.totalCost}</td>
              <td onClick={this.handleClickDetail}>
                <u>Chi tiết</u>
              </td>
            </tr>
          );
        case "refund":
          let total = 0;
          result.invoiceDetail.forEach((detail) => {
            total += (detail.price / detail.quantity) * detail.quantityRefund;
          });
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{result.id}</td>
              <td>{convertToLocaleString(result.date)}</td>
              <td>{total}</td>
              <td onClick={this.handleClickDetail}>
                <u>Chi tiết</u>
              </td>
            </tr>
          );
        default:
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{result.id}</td>
              <td>{convertToLocaleString(result.date)}</td>
              <td>{result.totalPrice}</td>
              <td onClick={this.handleClickDetail}>
                <u>Chi tiết</u>
              </td>
            </tr>
          );
      }
    });
    return detail;
  };
  render() {
    return (
      <div>
        <div className="invoice-container">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã Hoá Đơn</th>
                <th scope="col">Ngày lập</th>
                <th scope="col">Tổng tiền</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>{this.renderInvoiceResult()}</tbody>
          </table>
        </div>
        <div>
          {this.state.modalKey !== "" ? (
            <DetailModal
              show={this.state.showModal}
              type={this.props.type}
              toggleModal={this.toggleDetailModal}
              invoiceDetail={
                this.props.type === "import"
                  ? this.state.invoice[this.state.modalKey].productBatches
                  : this.state.invoice[this.state.modalKey].invoiceDetail
              }
            ></DetailModal>
          ) : null}
        </div>
      </div>
    );
  }
}
