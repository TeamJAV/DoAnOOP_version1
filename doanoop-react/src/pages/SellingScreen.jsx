import React, { Component } from "react";
import "../style/css/sellingScreen.css";

export default class SellingScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
      skuValue: "",
      searchResults: [],
      didSearch: false,
      totalPrice: 0,
      discount: 0,
      invoiceDetail: [],
    };
    this.baseState = this.state;
  }

  resetState = () => {
    this.setState(this.baseState);
  };

  resetSearch = () => {
    this.setState({
      skuValue: "",
      didSearch: false,
      searchResults: [],
    });
  };

  findIndexOfDetail = (sku) => {
    const index = this.state.invoiceDetail.findIndex((e) => {
      return e.sku === sku;
    });
    console.log(index)
    return index;
  };

  changeQuantity(type, quantity) {
    if (type) {
      return quantity + 1;
    } else {
      return quantity - 1;
    }
  }

  updateQuantity = (sku, type) => {
    const index = this.findIndexOfDetail(sku);
    let detail = [...this.state.invoiceDetail];
    const unitPrice = detail[index].unitPrice;
    const quantity = detail[index].quantity;
    const remain = detail[index].remain;

    if ((type && quantity === remain) || (!type && quantity === 1)) {
      return;
    }

    const newQuantity = this.changeQuantity(type, quantity);
    console.log(newQuantity);
    const newPrice = unitPrice * newQuantity;
    detail[index] = {
      ...detail[index],
      quantity: newQuantity,
      price: newPrice,
    };
    this.setState(
      {
        invoiceDetail: detail,
      },
      () => {
        this.updateTotalPrice();
      }
    );
  };

  updateTotalPrice = () => {
    let total = 0;
    console.log(total);
    if (this.state.invoiceDetail && this.state.invoiceDetail.length > 0) {
      this.state.invoiceDetail.map((result) => {
        return (total += parseFloat(result.price));
      });
    }
    if (this.state.discount > 0) {
      total -= total * this.state.discount;
    }
    this.setState({
      totalPrice: total,
    });
  };

  handleInputChange = (event) => {
    const value = event.target.value;
    if (value === "") {
      this.resetSearch();
      return;
    }
    this.setState(
      {
        skuValue: value,
        didSearch: false,
      },
      () => {
        console.log(this.state);
        const url = `http://localhost:8081/find-sku/${this.state.skuValue}`;
        fetch(url, {
          method: "GET",
        })
          .then((res) => {
            return res.json();
          })
          .then((data) => {
            this.setState({
              searchResults: data,
              didSearch: true,
            });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    );
  };

  handleInputOnBlur = () => {
    this.resetSearch();
  };

  handleSelectResult = (event) => {
    const sku = event.currentTarget.dataset.sku;
    if (this.findIndexOfDetail(sku) !== -1) {
      this.updateQuantity(sku, true)
      return;
    }
    const result = this.state.searchResults.find((e) => {
      return e.sku === sku;
    });
    const newDetail = {
      sku: result.sku,
      name: result.products.name,
      unitPrice: result.products.price,
      remain: result.quantity,
      quantity: 1,
      price: result.products.price,
    };
    this.setState(
      (prevState) => ({
        invoiceDetail: [...prevState.invoiceDetail, newDetail],
      }),
      () => {
        this.updateTotalPrice();
      }
    );
  };

  handleDeleteDetail = (event) => {
    const invoiceDetail = this.state.invoiceDetail;
    const sku = event.currentTarget.parentNode.dataset.sku;
    invoiceDetail.splice(
      invoiceDetail.findIndex((e) => e.sku === sku),
      1
    );
    console.log(invoiceDetail);
    this.setState(
      {
        totalPrice: 0,
        invoiceDetail: invoiceDetail,
      },
      () => {
        this.updateTotalPrice();
      }
    );
  };

  handleDecreaseValue = (event) => {
    const sku = event.currentTarget.parentNode.parentNode.dataset.sku;
    this.updateQuantity(sku, false);
  };

  handleIncreaseValue = (event) => {
    const sku = event.currentTarget.parentNode.parentNode.dataset.sku;
    this.updateQuantity(sku, true);
  };

  renderInvoiceResult() {
    if (this.state.invoiceDetail && this.state.invoiceDetail.length > 0) {
      let detail = this.state.invoiceDetail.map((result) => {
        return (
          <tr key={result.sku} data-sku={result.sku}>
            <th scope="row">{result.sku}</th>
            <td>{result.name}</td>
            <td data-remain={result.remain}>{result.remain}</td>
            <td className="change-quantity">
              <div
                className="value-button decrease"
                onClick={this.handleDecreaseValue}
                value="Decrease Value"
              >
                -
              </div>
              <div className="quantity-value" data-quantity={result.quantity}>
                {result.quantity}
              </div>
              <div
                className="value-button increase"
                onClick={this.handleIncreaseValue}
                value="Increase Value"
              >
                +
              </div>
            </td>
            <td>{result.unitPrice}</td>
            <td>{result.price}</td>
            <td onClick={this.handleDeleteDetail}>Xóa</td>
          </tr>
        );
      });
      return detail;
    } else {
      return null;
    }
  }

  renderSearchResults() {
    if (this.state.searchResults && this.state.searchResults.length > 0) {
      let list = this.state.searchResults.map((result) => {
        return (
          <div
            key={result.sku}
            data-sku={result.sku}
            onMouseDown={this.handleSelectResult}
          >
            <div>{result.sku}</div>
            <div>{result.products.name}</div>
            <div>{result.quantity}</div>
            <div>{result.price}</div>
          </div>
        );
      });
      return list;
    } else {
      if (this.state.didSearch) {
        return <p>Khong tim thay ket qua nao</p>;
      } else {
        return null;
      }
    }
  }

  render() {
    return (
      <div className="container">
        <div className="search-container">
          <input
            type="text"
            className="sku-search-bar"
            placeholder="Nhập mã sku..."
            value={this.state.skuValue}
            onChange={this.handleInputChange}
            onBlur={this.handleInputOnBlur}
          />
        </div>
        <div className="results-container">{this.renderSearchResults()}</div>
        <div className="invoice-container">
          <div className="invoice-detail">
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">SKU</th>
                  <th scope="col">Ten san pham</th>
                  <th scope="col">Con lai</th>
                  <th scope="col">So luong mua</th>
                  <th scope="col">Don gia</th>
                  <th scope="col">Thanh tien</th>
                  <th scope="col">#</th>
                </tr>
              </thead>
              <tbody>{this.renderInvoiceResult()}</tbody>
            </table>
          </div>
          <h4 className="discount">Giảm giá: {this.state.discount}</h4>
          <h2 className="total-price">Thành tiền: {this.state.totalPrice}</h2>
        </div>
      </div>
    );
  }
}
