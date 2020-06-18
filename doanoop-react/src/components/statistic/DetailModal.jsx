import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";
import { isArrayNull } from "../../utils/array";

class DetailModal extends Component {
  renderInvoiceResult = () => {
    console.log(this.props.invoiceDetail);
    if (isArrayNull(this.props.invoiceDetail)) {
      return null;
    }
    let key = 0;
    let detail = this.props.invoiceDetail.map((result) => {
      const batch = result.productBatches;
      switch (this.props.type) {
        case "import":
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{result.sku}</td>
              <td>{result.products.name}</td>
              <td>{result.expiredDate}</td>
              <td>{result.importQuantity}</td>
              <td>{result.importCost}</td>
              <td>{result.importCost * result.importQuantity}</td>
            </tr>
          );
        case "refund":
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{batch.sku}</td>
              <td>{batch.products.name}</td>
              <td>{batch.expiredDate}</td>
              <td>{result.quantityRefund}</td>
              <td>{batch.products.price}</td>
              <td>{result.quantityRefund * batch.products.price}</td>
            </tr>
          );
        default:
          return (
            <tr data-key={key} key={key++}>
              <th scope="row">{key}</th>
              <td>{batch.sku}</td>
              <td>{batch.products.name}</td>
              <td>{batch.expiredDate}</td>
              <td>{result.quantity}</td>
              <td>{batch.products.price}</td>
              <td>{result.price}</td>
            </tr>
          );
      }
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
            className="detail-modal"
          >
            <Modal.Body>
              <div>
                <div className="invoice-container">
                  <table className="table">
                    <thead>
                      <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Mã SKU</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Ngày hết hạn</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Đơn giá</th>
                        <th scope="col">Tổng tiền</th>
                      </tr>
                    </thead>
                    <tbody>{this.renderInvoiceResult()}</tbody>
                  </table>
                </div>
              </div>
            </Modal.Body>
            <Modal.Footer>
              <Button
                className="btn btn-secondary btn-cancel"
                onClick={() => {
                  this.props.toggleModal("");
                }}
              >
                Quay lại
              </Button>
            </Modal.Footer>
          </Modal>
        </div>
      </>
    );
  }
}

export default DetailModal;
