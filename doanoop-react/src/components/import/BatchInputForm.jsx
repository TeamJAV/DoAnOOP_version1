import React, { Component } from "react";
import SearchBar from "./SearchBar";

export default class BatchInputForm extends Component {
  constructor(props) {
    super(props);
    this.baseState = {
      importDate: "2020-12-12",
      expiredDate: "2020-12-12",
      quantity: null,
      importQuantity: null,
      importCost: null,
      products: {
        id: "",
        name: "",
      },
      suppliers: {
        id: "",
        name: "",
      },
    };
    this.state = this.baseState;
    this.keyCount = 0;
  }

  setProduct = (product) => {
    this.setState({
      products: {
        id: product.id,
        name: product.name,
      },
    });
    console.log(this.state);
  };

  setSupplier = (supplier) => {
    this.setState({
      suppliers: {
        id: supplier.id,
        name: supplier.name,
      },
    });
    console.log(this.state);
  };

  setKey = () => {
    this.keyCount += 1;
  };

  handleClickReset = (event) => {
    event.preventDefault();
    this.setKey();
    this.setState(this.baseState);
    console.log(this.state);
  };

  handleClickContinue = (event) => {
    event.preventDefault();
    this.props.setBatches(this.state);
  };

  handleInputChange = (event) => {
    
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
            <SearchBar
              type="product"
              onSelect={this.setProduct}
              key={this.keyCount}
            />
          </div>
          <div
            className="batch-form__batch-info"
            style={{ display: "inline-block", width: "50%" }}
          >
            <div className="batch-form__supplier">
              <span>Nhà cung cấp: </span>
              <SearchBar
                type="supplier"
                onSelect={this.setSupplier}
                key={this.keyCount}
              />
            </div>
            <div className="batch-form__expire-date">
              <span>Ngày hết hạn: </span>
              <input
                type="date"
                name="expiredDate"
                value={this.state.expiredDate}
                onChange={this.handleInputChange}
              ></input>
            </div>
            <div className="batch-form__quantity">
              <span>Số lượng: </span>
              <input
                type="text"
                name="quantity"
                value={this.state.quantity}
                onChange={this.handleInputChange}
              ></input>
            </div>
            <div className="batch-form__price">
              <span>Giá nhập: </span>
              <input
                type="text"
                name="importCost"
                value={this.state.importCost}
                onChange={this.handleInputChange}
              ></input>
            </div>
          </div>
          <div className="batch-form__button">
            <button onClick={this.handleClickReset}>Tạo lại</button>
            <button onClick={this.handleClickContinue}>Tiếp tục</button>
          </div>
        </form>
      </>
    );
  }
}
