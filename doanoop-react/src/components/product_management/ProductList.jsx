import React, { Component } from "react";
import { isArrayNull } from "../../utils/array";
import ProductModal from "./ProductModal";

class ProductList extends Component {
  state = {
    productNameInput: "",
    products: [],
    modalKey: "",
    modalType: "",
    showModal: false,
  };

  componentDidMount() {
    this.getProduct();
  }

  handleFormSubmit = (event) => {
    event.preventDefault();
    this.getProduct();
  };

  getProduct = () => {
    fetch(
      `http://localhost:8081/product/find?q=${this.state.productNameInput}`,
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
        this.setState({ products: data }, () => {
          console.log(this.state.products);
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  handleInputChange = (event) => {
    this.setState({
      productNameInput: event.target.value,
    });
  };

  handleChangePrice = (event) => {
    const key = event.currentTarget.parentNode.dataset.key;
    console.log(key);
    this.toggleDetailModal(key, "productPrice");
  };

  handleClickDetail = (event) => {
    const key = event.currentTarget.parentNode.dataset.key;
    console.log(key);
    this.toggleDetailModal(key, "productDetail");
  };

  toggleDetailModal = (key = "", type = "") => {
    this.setState({
      modalKey: key,
      modalType: type,
      showModal: !this.state.showModal,
    });
  };

  renderProduct = () => {
    if (isArrayNull(this.state.products)) {
      return null;
    }
    let key = 0;
    let products = this.state.products.map((result) => {
      return (
        <tr data-key={key} key={key++}>
          <th className="t-center" scope="row">
            {key}
          </th>
          <td className="t-center">{result.id}</td>
          <td>{result.name}</td>
          <td onClick={this.handleChangePrice}>{result.price}</td>
          <td className="t-center" onClick={this.handleClickDetail}>
            <u>Chi tiết</u>
          </td>
        </tr>
      );
    });
    return products;
  };

  render() {
    return (
      <>
        <div className="products-container">
          <form className="products-search" onSubmit={this.handleFormSubmit}>
            <input
              type="text"
              className="products-search__input"
              onChange={this.handleInputChange}
              value={this.state.productNameInput}
              placeholder="Nhập tên hàng..."
            ></input>
          </form>
          <table className="table">
            <thead>
              <tr>
                <th className="t-center" scope="col">
                  STT
                </th>
                <th className="t-center" scope="col">
                  Mã hàng
                </th>
                <th scope="col">Tên mặt hàng</th>
                <th scope="col">Giá bán</th>
                <th className="t-center" scope="col">
                  #
                </th>
              </tr>
            </thead>
            <tbody>{this.renderProduct()}</tbody>
          </table>
          <div>
            <ProductModal
              show={this.state.showModal}
              type={this.state.modalType}
              toggleModal={this.toggleDetailModal}
              productDetail={this.state.products[this.state.modalKey]}
            ></ProductModal>
          </div>
        </div>
      </>
    );
  }
}

export default ProductList;
