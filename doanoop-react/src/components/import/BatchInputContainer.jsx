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
    console.log("abc");
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
    return (
      <>
        <BatchInputForm setBatches={this.setProductBatches} />
        <NewBatchList
          listBatches={this.state.productBatches}
          deleteBatch={this.deleteProductBatch}
        ></NewBatchList>
        <div className="action-button">
          <Button
            className="btn btn-primary btn-confirm"
            onClick={this.handleToggleModal}
          >
            Hoàn thành
          </Button>
          <Button className="btn btn-secondary btn-cancel" href="/import">
            Hủy bỏ
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
