import React, { Component } from "react";
import SearchBar from "./SearchBar";

export default class BatchInputForm extends Component {
  constructor(props) {
    super(props);
    this.baseState = {
      productName: "",
      productId: "",
      supplierName: "",
      supplierId: "",
    };
    this.state = this.baseState;
    this.keyCount = 0;
  }

  setProduct = (product) => {
    this.setState({
      productName: product.name,
      productId: product.id,
    });
    console.log(this.state);
  };

  setSupplier = (supplier) => {
    this.setState({
      supplierName: supplier.name,
      supplierId: supplier.id,
    });
    console.log(this.state);
  };

  setKey = () => {
    this.keyCount += 1;
  }

  handleClickReset = (event) => {
    event.preventDefault();
    this.setKey();
    this.setState(this.baseState);
    console.log(this.state);
  };

  handleInputChange(field, value) {
    this.setState({
      [field]: value
    });
  }

  render() {
    return (
      <>
        <form style={{ width: 900 }} className="batch-form">
          <div
            className="batch-form__product-info"
            style={{ display: "inline-block", width: "50%" }}
          >
            <span>Tên hàng hóa: </span>
            <SearchBar type="product" onSelect={this.setProduct} key={this.keyCount} />
          </div>
          <div
            className="batch-form__batch-info"
            style={{ display: "inline-block", width: "50%" }}
          >
            <div className="batch-form__supplier">
              <span>Nhà cung cấp: </span>
              <SearchBar type="supplier" onSelect={this.setSupplier} key={this.keyCount} />
            </div>
            <div className="batch-form__expire-date">
              <span>Ngày hết hạn: </span>
              <input type="date"></input>
            </div>
            <div className="batch-form__quantity">
              <span>Số lượng: </span>
              <input type="text"></input>
            </div>
            <div className="batch-form__price">
              <span>Giá nhập: </span>
              <input type="text"></input>
            </div>
          </div>
          <div className="batch-form__button">
            <button onClick={this.handleClickReset}>Reset</button>
          </div>
        </form>
      </>
    );
  }
}
