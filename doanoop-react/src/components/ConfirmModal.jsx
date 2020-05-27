import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";
import "../style/css/confirmModal.css";

export default class ConfirmModal extends Component {
  renderModalContent = () => {
    switch (this.props.type) {
      case "selling-confirm":
        return (
          <div>
            <div>Xác nhận khách hàng đã thanh toán số tiền: </div>
            <div style={{ fontSize: 30 }}>{this.props.price} VND</div>
          </div>
        );
      case "selling-fetching":
        return (
          <div>
            <div>
              <div>Hoá đơn đang được xử lý</div>
              <div>Xin vui lòng chờ trong giây lát...</div>
            </div>
          </div>
        );
      case "selling-success":
        return (
          <div>
            <div>
              <div>Thành công</div>
              <div>Bấm hoàn tất để kết thúc giao dịch</div>
            </div>
          </div>
        );
      case "selling-failure":
        return "Đã xảy ra lỗi. Xin vui lòng thử lại";
      default:
        return null;
    }
  };

  renderModalButton = () => {
    switch (this.props.type) {
      case "selling-confirm":
        return (
          <>
            <Button
              className="btn btn-secondary btn-cancel"
              onClick={this.props.toggleModal}
            >
              Hủy bỏ
            </Button>
            <Button
              className="btn btn-primary btn-confirm"
              onClick={this.props.saveInvoice}
            >
              Đồng ý
            </Button>
          </>
        );
      case "selling-fetching":
        return null;
      case "selling-success":
        return (
          <>
            <Button className="btn btn-primary btn-confirm" href="selling">
              Hoàn tất
            </Button>
          </>
        );
      case "selling-failure":
        return "Đã xảy ra lỗi. Xin vui lòng thử lại";
      default:
        return null;
    }
  };

  render() {
    return (
      <>
        <Modal
          show={this.props.show}
          onHide={this.props.toggleModal}
          backdrop="static"
          animation={false}
          centered={true}
        >
          <Modal.Body>{this.renderModalContent()}</Modal.Body>
          <Modal.Footer>{this.renderModalButton()}</Modal.Footer>
        </Modal>
      </>
    );
  }
}
