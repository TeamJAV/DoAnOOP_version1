import React, { Component } from "react";
import SearchBar from "./SearchBar";

export default class BatchInputForm extends Component {
  constructor(props) {
    super(props);
    this.baseState = {
      importDate: new Date().toISOString().split("T")[0],
      expiredDate: "",
      importQuantity: "",
      quantity: "",
      importCost: "",
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
    const target = event.target;
    if (target.name === "importQuantity") {
      this.setState({
        [target.name]: target.value,
        quantity: target.value,
      });
    } else {
      this.setState({
        [target.name]: target.value,
      });
    }
  };

  render() {
    return (
      <>
        <form className="batch-form">
          <div
            className="batch-form__product-info"
            style={{
              display: "inline-block",
              width: "50%",
              verticalAlign: "top",
            }}
          >
            <div className="batch-form__name justify-between">
              <span>Tên hàng hóa: </span>
              <SearchBar
                type="product"
                onSelect={this.setProduct}
                key={this.keyCount}
              />
            </div>
          </div>
          <div
            className="batch-form__batch-info"
            style={{ display: "inline-block", width: "50%" }}
          >
            <div className="batch-form__supplier justify-between">
              <span>Nhà cung cấp: </span>
              <SearchBar
                type="supplier"
                onSelect={this.setSupplier}
                key={this.keyCount}
              />
            </div>
            <div className="batch-form__expire-date justify-between">
              <span>Ngày hết hạn: </span>
              <input
                type="date"
                name="expiredDate"
                value={this.state.expiredDate}
                onChange={this.handleInputChange}
              ></input>
            </div>
            <div className="batch-form__quantity justify-between">
              <span>Số lượng: </span>
              <input
                type="text"
                name="importQuantity"
                value={this.state.importQuantity}
                onChange={this.handleInputChange}
                autoComplete="off"
              ></input>
            </div>
            <div className="batch-form__price justify-between">
              <span>Giá nhập: </span>
              <input
                type="text"
                name="importCost"
                value={this.state.importCost}
                onChange={this.handleInputChange}
                autoComplete="off"
              ></input>
            </div>
          </div>
          <div className="batch-form__button">
            <button
              className="btn-type-2 btn-cancel"
              onClick={this.handleClickReset}
            >
              Tạo lại
            </button>
            <button
              className="btn-type-2 btn-confirm"
              onClick={this.handleClickContinue}
            >
              Tiếp tục
            </button>
          </div>
        </form>
      </>
    );
  }
}
