import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";
import { isArrayNull } from "../../utils/array";
import { isValidPrice } from "../../utils/validate";

class ProductModal extends Component {
  state = {
    price: "",
  };

  handleChangePrice = (event) => {
    this.setState({
      price: event.target.value,
    });
  };

  handleSubmitNewPrice = (event) => {
    event.preventDefault();
    const price = parseInt(this.state.price);
    if (!isValidPrice(price)) return;
    fetch(
      `http://localhost:8081/product/change/${this.props.productDetail.id}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: this.props.productDetail.id,
          price: price,
        }),
      }
    )
      .then((res) => {
        window.location.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  renderModalContent = () => {
    switch (this.props.type) {
      case "productDetail":
        return (
          <table className="table">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã SKU</th>
                <th scope="col">Ngày nhập</th>
                <th scope="col">Ngày hết hạn</th>
                <th scope="col">Nhà cung cấp</th>
                <th scope="col">SL Tồn</th>
                <th scope="col">SL Nhập</th>
                <th scope="col">Đơn giá nhập</th>
                <th scope="col">Tổng tiền</th>
              </tr>
            </thead>
            <tbody>{this.renderInvoiceResult()}</tbody>
          </table>
        );
      case "productPrice":
        const productBatches = this.props.productDetail.productBatches;
        const averageCost = isArrayNull(productBatches)
          ? 0
          : productBatches.reduce((sum, batch) => {
              return sum + batch.importCost;
            }, 0) / productBatches.length;
        return (
          <>
            <div className="supplier-modal-content">
              <div className="supplier-modal-content-left">
                <div>Mã hàng:</div>
                <div>Tên mặt hàng: </div>
                <div>Đơn giá nhập &nbsp;&#40;Trung Bình&#41;:</div>
                <div>Đơn giá bán &nbsp;&#40;Hiện tại&#41;:</div>
              </div>
              <div className="supplier-modal-content-right">
                <div>{this.props.productDetail.id}</div>
                <div>{this.props.productDetail.name}</div>
                <div>{averageCost}</div>
                <div>{this.props.productDetail.price}</div>
              </div>
            </div>
            <form onSubmit={this.handleSubmitNewPrice}>
              <p
                style={{
                  display: "inline-block",
                  marginRight: "10px",
                  fontSize: "20px",
                }}
              >
                Giá bán mới:
              </p>
              <input
                value={this.state.price}
                onChange={this.handleChangePrice}
                type="number"
                id="changePriceInput"
                min="500"
                step="100"
                required
                style={{ width: "12rem", fontSize: "20px" }}
              />
            </form>
          </>
        );

      default:
        return null;
    }
  };

  renderInvoiceResult = () => {
    const productBatch = this.props.productDetail.productBatches;
    console.log(productBatch);
    if (isArrayNull(productBatch)) {
      return null;
    }
    let key = 0;
    let detail = productBatch.map((result) => {
      return (
        <tr data-key={key} key={key++}>
          <th scope="row">{key}</th>
          <td>{result.sku}</td>
          <td>{result.importInvoice.importDate}</td>
          <td>{result.expiredDate}</td>
          <td>{result.suppliers.name}</td>
          <td>{result.quantity}</td>
          <td>{result.importQuantity}</td>
          <td>{result.importCost}</td>
          <td>{result.importInvoice.totalCost}</td>
        </tr>
      );
    });
    return detail;
  };
  render() {
    return (
      <>
        <div>
          <Modal
            show={this.props.show}
            onHide={this.props.toggleModal}
            backdrop="static"
            animation={false}
            centered={true}
            className={this.props.type + "-modal"}
          >
            <Modal.Body>
              <div>
                <div className="invoice-container">
                  {this.renderModalContent()}
                </div>
              </div>
            </Modal.Body>
            <Modal.Footer>
              <div>
                <Button
                  className="btn btn-secondary btn-cancel"
                  onClick={() => {
                    this.props.toggleModal();
                  }}
                >
                  Quay lại
                </Button>
                {this.props.type === "productPrice" ? (
                  <button
                    className="btn-type-2 btn-confirm"
                    onClick={this.handleSubmitNewPrice}
                  >
                    Thay đổi
                  </button>
                ) : null}
              </div>
            </Modal.Footer>
          </Modal>
        </div>
      </>
    );
  }
}

export default ProductModal;
