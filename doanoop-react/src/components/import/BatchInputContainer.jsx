import React, { Component } from "react";
import BatchInputForm from "./BatchInputForm";
import NewBatchList from "./NewBatchList";
import { sortByProductName, isArrayNull } from "../../utils/array";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal";

export default class BatchInputContainer extends Component {
  state = {
    productBatches: [],
    modal: {
      type: "",
      show: false,
    },
  };

  setProductBatches = (batch) => {
    const productBatches = [...this.state.productBatches, batch];
    productBatches.sort((a, b) => {
      return sortByProductName(a, b);
    });
    this.setState({
      productBatches: productBatches,
    });
  };

  deleteProductBatch = (batchIndex) => {
    console.log(batchIndex);
    const productBatches = [...this.state.productBatches];
    productBatches.splice(batchIndex, 1);
    this.setState({
      productBatches: productBatches,
    });
  };

  /**-------------------------------------------------- */
  handleSaveInvoice = () => {
    fetch("http://localhost:8081/import/invoice", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        productBatches: this.state.productBatches,
      }),
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        this.setState({
          modal: {
            type: "success",
            show: this.state.modal.show,
          },
        });
      })
      .catch((err) => {
        console.log(err);
        this.setState({
          modal: {
            type: "failure",
            show: this.state.modal.show,
          },
        });
      });
  };
  /**-------------------------------------------------- */

  handleToggleModal = () => {
    if (isArrayNull(this.state.productBatches)) {
      return;
    }
    this.setState({
      modal: {
        type: "import-batches-confirm",
        show: !this.state.modal.show,
      },
    });
  };

  render() {
    const listStyle = {
      maxWidth: "977px",
      margin: "0 auto",
    };
    return (
      <>
        <BatchInputForm setBatches={this.setProductBatches} />
        <div className="batch-list__header">
          <p>Danh sách hàng đã nhập</p>
        </div>
        <NewBatchList
          listBatches={this.state.productBatches}
          deleteBatch={this.deleteProductBatch}
          listStyle={listStyle}
        ></NewBatchList>
        <div className="action-button justify-center">
          <Button className="btn-type-2 btn-cancel" href="/import">
            Hủy bỏ
          </Button>
          <Button
            className="btn-type-2 btn-confirm"
            onClick={this.handleToggleModal}
          >
            Hoàn tất
          </Button>
        </div>
        <ConfirmModal
          show={this.state.modal.show}
          type={this.state.modal.type}
          toggleModal={this.handleToggleModal}
          proceed={this.handleSaveInvoice}
        ></ConfirmModal>
      </>
    );
  }
}
