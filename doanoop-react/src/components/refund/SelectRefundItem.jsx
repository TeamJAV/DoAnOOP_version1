import React, { Component } from "react";

export default class SelectRefundItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedItem: "",
      refundQuantity: "",
    };
  }

  handleChangeOption = (event) => {
    this.setState({
      selectedItem: event.target.value,
      refundQuantity: 1,
    });
  };

  handleChangeQuantity = (event) => {
    let value = event.target.value;
    const maxQuantity = this.props.detail[this.state.selectedItem].quantity;
    if (value !== "" && value < 1) {
      value = 1;
    } else if (value > maxQuantity) {
      value = maxQuantity;
    }
    this.setState({
      refundQuantity: parseInt(value),
    });
  };

  handleAddButton = (event) => {
    event.preventDefault();
    const item = this.props.detail[this.state.selectedItem];
    const itemBatch = item.productBatches;
    const newDetail = {
      id: item.id,
      quantityRefund: this.state.refundQuantity,
      quantity: item.quantity,
      productBatches: {
        sku: itemBatch.sku,
        products: {
          id: itemBatch.products.id,
          name: itemBatch.products.name,
          price: itemBatch.products.price,
        },
      },
    };
    this.props.setDetail(newDetail);
  };

  renderOptions = () => {
    let key = 0;
    return this.props.detail.map((opt) => {
      return (
        <option value={key++} key={opt.id}>
          {opt.productBatches.products.name}
        </option>
      );
    });
  };

  render() {
    let isSelected = false;
    let maxQuantity;
    if (this.state.selectedItem !== "") {
      isSelected = true;
      maxQuantity = this.props.detail[this.state.selectedItem].quantity;
    }
    return (
      <>
        <form id="selectRefundForm">
          <div className="inline-block">
            <span className="mr25">Sản phẩm:</span>
            <select
              className="refund-options input-type-1"
              onChange={this.handleChangeOption}
              defaultValue="default"
            >
              <option value="default" disabled hidden>
                -- --
              </option>
              {this.renderOptions()}
            </select>
          </div>
          <div className="inline-block">
            <span className="mr25">Số lượng:</span>
            <input
              type="number"
              className="refund-quantity input-type-1"
              value={this.state.refundQuantity}
              min="1"
              max={maxQuantity}
              onChange={this.handleChangeQuantity}
              disabled={!isSelected}
            />
          </div>
          <button
            className="btn-type-2 btn-confirm"
            disabled={!isSelected}
            onClick={this.handleAddButton}
            style={{ width: "85px", padding: "6px" }}
          >
            Thêm
          </button>
        </form>
      </>
    );
  }
}
