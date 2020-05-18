import React, { Component } from "react";
import "../style/css/sellingScreen.css";
import NavigationBar from "../components/NavigationBar";
import ConfirmModal from "../components/ConfirmModal";
import { Button } from "react-bootstrap";

export default class SellingScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
      skuValue: "",
      searchResults: [],
      didSearch: false,
      tempPrice: 0,
      totalPrice: 0,
      discount: 0,
      invoiceDetail: [],
      showModal: false,
      modalType: "",
    };
  }

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
    return index;
  };

  changeQuantity(type, quantity) {
    if (type) {
      return quantity + 1;
    } else {
      return quantity - 1;
    }
  }

  isArrayNull(arr) {
    if (arr && arr.length > 0) {
      return false;
    } else {
      return true;
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
    if (this.state.invoiceDetail && this.state.invoiceDetail.length > 0) {
      this.state.invoiceDetail.map((result) => {
        return (total += parseFloat(result.price));
      });
    }
    this.setState({
      totalPrice: total,
      tempPrice: total - total * this.state.discount,
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
      this.updateQuantity(sku, true);
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

  handleToggleModal = () => {
    if (this.isArrayNull(this.state.invoiceDetail)) {
      return;
    }
    this.setState(
      {
        modalType: "selling-confirm",
        showModal: !this.state.showModal,
      }
    );
  };

  handleSaveInvoice = (event) => {
    if (this.isArrayNull(this.state.invoiceDetail)) {
      return;
    }
    let date = new Date();
    const urlSelling = "http://localhost:8081/saveSellingInvoice";
    const urlDetail = "http://localhost:8081/saveInvoiceDetail";
    this.setState(
      {
        modalType: "selling-fetching",
      },
      () => {
        fetch(urlSelling, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            date: date.getTime(),
            totalPrice: this.state.totalPrice,
            discount: this.state.discount,
          }),
        })
          .then((res) => {
            return res.json();
          })
          .then((data) => {
            let invoiceDetail = [...this.state.invoiceDetail];
            invoiceDetail.forEach((detail) => {
              detail.sellingInvoice = {
                id: data.id,
              };
              detail.productBatches = {
                sku: detail.sku,
              };
            });
            fetch(urlDetail, {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(invoiceDetail),
            })
              .then((res) => {
                return res.json();
              })
              .then((data) => {
                this.setState({
                  modalType: "selling-success",
                });
              })
              .catch((err) => {
                this.setState({
                  modalType: "selling-failure",
                });
              });
          })
          .catch((err) => {
            this.setState({
              modalType: "selling-failure",
            });
          });
      }
    );
  };

  renderInvoiceResult = () => {
    if (this.isArrayNull(this.state.invoiceDetail)) {
      return null;
    }
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
  }

  renderSearchResults = () => {
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
      <>
        <NavigationBar></NavigationBar>
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
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">SKU</th>
                  <th scope="col">Tên sản phẩm</th>
                  <th scope="col">Còn lại</th>
                  <th scope="col">Số lượng mua</th>
                  <th scope="col">Đơn giá</th>
                  <th scope="col">Thành tiền</th>
                  <th scope="col">#</th>
                </tr>
              </thead>
              <tbody>{this.renderInvoiceResult()}</tbody>
            </table>
          </div>

          <div className="total-money">
            <table className="total-money-table">
              <tbody>
                <tr>
                  <td>Tổng tiền:</td>
                  <td>{this.state.tempPrice}</td>
                </tr>
                <tr>
                  <td>Giảm giá:</td>
                  <td>{this.state.discount}</td>
                </tr>
                <tr>
                  <td>Tổng tiền thanh toán:</td>
                  <td>{this.state.totalPrice}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <Button variant="secondary" href="/selling">
            Hủy bỏ
          </Button>
          <Button variant="primary" onClick={this.handleToggleModal}>
            Thanh toán
          </Button>
        </div>
        <ConfirmModal
          show={this.state.showModal}
          type={this.state.modalType}
          toggleModal={this.handleToggleModal}
          saveInvoice={this.handleSaveInvoice}
        ></ConfirmModal>
      </>
    );
  }
}
