import React, { Component } from "react";
import ConfirmModal from "../ConfirmModal";
import { Button } from "react-bootstrap";

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
        console.log(res)
        return res.json();
      })
      .then((data) => {
        this.setState({
          modal: {
            type: "success",
            show: true
          },
        }, () => {
          console.log(this.state.modal)
        });
      })
      .catch((err) => {
        this.setState({
          modal: {
            type: "failure",
            show: true
          },
        });
      });
  };
  /**-------------------------------------------------- */

  render() {
    return (
      <>
        <form className="product-form">
          <div className="product-form__name">
            <span>Tên hàng hóa: </span>
            <input
              type="text"
              name="name"
              value={this.state.product.name}
              onChange={this.handleInputChange}
            ></input>
          </div>
          <div className="product-form__price">
            <span>Giá bán: </span>
            <input
              type="text"
              name="price"
              value={this.state.product.price}
              onChange={this.handleInputChange}
            ></input>
          </div>
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
            proceed={this.handleSaveProduct}
          ></ConfirmModal>
        </form>
      </>
    );
  }
}
