import React, { Component } from "react";
import SelectRefundItem from "./SelectRefundItem";
import BatchList from "../BatchList";
import {
  findIndexById,
  sortByProductName,
  isArrayNull,
} from "../../utils/array";
import ConfirmModal from "../ConfirmModal";

export default class CreateRefundInvoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newRefundInvoice: {
        refundInvoice: {
          date: new Date().toISOString(),
          note: "",
        },
        invoiceDetail: [],
      },
      showModal: false,
      modalType: "",
    };
  }
  updateRefundQuantity = (detail, quantity) => {
    if (detail.quantityRefund + quantity > detail.quantity) {
      detail.quantityRefund = quantity;
    } else {
      detail.quantityRefund += quantity;
    }
  };
  setInvoiceDetail = (newDetail) => {
    const invoiceDetail = [...this.state.newRefundInvoice.invoiceDetail];
    const index = findIndexById(newDetail.id, invoiceDetail);
    if (index === -1) {
      //neu chua ton tai thi push vao arr clone
      invoiceDetail.push(newDetail);
      //sort arr clone
      invoiceDetail.sort((a, b) => {
        return sortByProductName(a.productBatches, b.productBatches);
      });
    } else {
      this.updateRefundQuantity(invoiceDetail[index], newDetail.quantityRefund);
    }
    this.setState((prevState) => ({
      newRefundInvoice: {
        ...prevState.newRefundInvoice,
        invoiceDetail: invoiceDetail,
      },
    }));
  };
  deleteDetail = (id) => {
    const invoiceDetail = [...this.state.newRefundInvoice.invoiceDetail];
    const index = findIndexById(id, invoiceDetail);
    // const sku = event.currentTarget.parentNode.dataset.sku;
    invoiceDetail.splice(index, 1);
    this.setState((prevState) => ({
      newRefundInvoice: {
        ...prevState.newRefundInvoice,
        invoiceDetail: invoiceDetail,
      },
    }));
  };
  handleToggleModal = () => {
    if (isArrayNull(this.state.newRefundInvoice.invoiceDetail)) {
      return;
    }
    this.setState({
      modalType: "refund-confirm",
      showModal: !this.state.showModal,
    });
  };
  createRefundInvoice = () => {
    this.setState(
      {
        modalType: "fetching",
      },
      () => {
        fetch("http://localhost:8081/refund/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.state.newRefundInvoice),
        })
          .then((res) => {
            return res.json();
          })
          .then((data) => {
            this.setState({
              modalType: "success",
            });
          })
          .catch((err) => {
            this.setState({
              modalType: "failure",
            });
          });
      }
    );
  };
  render() {
    let total = 0;
    const invoiceDetail = this.state.newRefundInvoice.invoiceDetail;
    if (!isArrayNull(invoiceDetail)) {
      invoiceDetail.forEach((detail) => {
        total += detail.quantityRefund * detail.productBatches.products.price;
      });
    }
    return (
      <>
        <SelectRefundItem
          detail={this.props.detail}
          setDetail={this.setInvoiceDetail}
        ></SelectRefundItem>
        <BatchList
          list={this.state.newRefundInvoice.invoiceDetail}
          type="refund"
          isDeleteAllowed={true}
          delete={this.deleteDetail}
        ></BatchList>
        <div>Tổng tiền trả lại: {total}</div>
        <button className="btn-type-2 btn-cancel" onClick={this.props.cancel}>
          Quay lại
        </button>
        <button
          className="btn-type-2 btn-confirm"
          onClick={this.handleToggleModal}
          style={{ padding: "6px 18px" }}
        >
          Hoàn tiền
        </button>
        <ConfirmModal
          show={this.state.showModal}
          type={this.state.modalType}
          toggleModal={this.handleToggleModal}
          proceed={this.createRefundInvoice}
        ></ConfirmModal>
      </>
    );
  }
}
