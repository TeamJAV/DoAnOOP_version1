import React, { Component } from "react";
import ConfirmModal from "../ConfirmModal";

export default class ProductInputForm extends Component {
  state = {
    product: {
      name: "",
      price: "",
    },
    modal: {
      type: "",
      show: false,
    },
  };

  handleInputChange = (event) => {
    const target = event.target;
    this.setState((prevState) => ({
      product: {
        ...prevState.product,
        [target.name]: target.value,
      },
    }));
  };

  handleCancelButton = (event) => {
    event.preventDefault();
    this.setState({
      product: {
        name: "",
        price: "",
      }
    });
  };

  handleToggleModal = () => {
    const product = this.state.product;
    if (product.name === "" || product.price === "") {
      return;
    }
    this.setState({
      modal: {
        type: "import-product-confirm",
        show: !this.state.modal.show,
      },
    });
  };

  /**-------------------------------------------------- */
  handleSaveProduct = () => {
    const url = "http://localhost:8081/import/product";
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: this.state.product.name,
        price: this.state.product.price,
      }),
    })
      .then((res) => {
        console.log(res);
        return res.json();
      })
      .then((data) => {
        this.setState(
          {
            modal: {
              type: "success",
              show: true,
            },
          },
          () => {
            console.log(this.state.modal);
          }
        );
      })
      .catch((err) => {
        this.setState({
          modal: {
            type: "failure",
            show: true,
          },
        });
      });
  };
  /**-------------------------------------------------- */

  render() {
    return (
      <>
        <form className="product-form">
          <div className="product-form__name justify-between">
            <span>Tên hàng hóa: </span>
            <input
              type="text"
              name="name"
              value={this.state.product.name}
              onChange={this.handleInputChange}
              autoComplete="off"
            ></input>
          </div>
          <div className="product-form__price justify-between">
            <span>Giá bán: </span>
            <input
              type="text"
              name="price"
              value={this.state.product.price}
              onChange={this.handleInputChange}
              autoComplete="off"
            ></input>
          </div>
        </form>
        <div className="action-button">
          <button
            className="btn-type-2 btn-cancel"
            href="/import"
            onClick={this.handleCancelButton}
          >
            Hủy bỏ
          </button>
          <button
            className="btn-type-2 btn-confirm"
            onClick={this.handleToggleModal}
            style={{ width: "140px", padding: "6px 3px" }}
          >
            Hoàn thành
          </button>
        </div>
        <ConfirmModal
          show={this.state.modal.show}
          type={this.state.modal.type}
          toggleModal={this.handleToggleModal}
          proceed={this.handleSaveProduct}
        ></ConfirmModal>
      </>
    );
  }
}
