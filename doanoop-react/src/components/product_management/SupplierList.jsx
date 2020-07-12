import React, { Component } from "react";
import { isArrayNull } from "../../utils/array";
import SupplierModal from "./SupplierModal";

class SupplierList extends Component {
  state = {
    id: "",
    name: "",
    address: "",
    tel: "",
    suppliers: [],
    modalKey: "",
    modalType: "",
    showModal: false,
  };

  componentDidMount = () => {
    this.getSuppliers();
  };

  reloadSupplier = async () => {
    try {
      await this.resetSupplier();
      await this.toggleDetailModal();
      await this.componentDidMount();
    } catch (err) {
      console.log(err);
    }
  };

  fetchSupplier = () => {
    let url = "",
      method = "";
    switch (this.state.modalType) {
      case "create":
        url = `http://localhost:8081/supply/supplier`;
        method = "POST";
        break;
      case "update":
        url = `http://localhost:8081/supply/supplier/${this.state.id}`;
        method = "PUT";
        break;
      default:
        return;
    }
    fetch(url, {
      method: method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: this.state.name,
        address: this.state.address,
        phoneNumber: this.state.tel,
      }),
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.reloadSupplier();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  handleClickDetail = (event) => {
    const key = event.currentTarget.parentNode.dataset.key;
    const sup = this.state.suppliers[key];
    console.log(sup);
    this.setState(
      {
        id: sup.id,
        name: sup.name,
        address: sup.address,
        tel: sup.phoneNumber,
      },
      () => {
        this.toggleDetailModal("", "update");
      }
    );
  };

  handleNewSupplier = () => {
    this.toggleDetailModal("", "create");
  };

  setSupplier = (event) => {
    const target = event.target;
    this.setState(
      {
        [target.name]: target.value,
      },
      () => {
        console.log(this.state);
      }
    );
  };

  resetSupplier = () => {
    this.setState({
      id: "",
      name: "",
      address: "",
      tel: "",
    });
  };

  getSupplierState = (val) => {
    return this.state[val];
  };

  toggleDetailModal = (key = "", type = "") => {
    this.setState({
      modalKey: key,
      modalType: type,
      showModal: !this.state.showModal,
    });
  };

  getSuppliers = () => {
    fetch(`http://localhost:8081/supply/all_supplier`, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.setState({ suppliers: data }, () => {
          console.log(this.state.suppliers);
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  renderSuppliers = () => {
    if (isArrayNull(this.state.suppliers)) {
      return null;
    }
    let key = 0;
    let suppliers = this.state.suppliers.map((result) => {
      return (
        <tr data-key={key} key={key++}>
          <th scope="row">{key}</th>
          <td>{result.name}</td>
          <td>{result.address}</td>
          <td>{result.phoneNumber}</td>
          <td onClick={this.handleClickDetail}>
            <u>Chi tiết</u>
          </td>
        </tr>
      );
    });
    return suppliers;
  };
  render() {
    return (
      <>
        <div className="suppliers-container">
          <div className="sup-table">
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">STT</th>
                  <th scope="col">Tên nhà cung cấp</th>
                  <th scope="col">Địa chỉ</th>
                  <th scope="col">Số điện thoại</th>
                  <th scope="col">#</th>
                </tr>
              </thead>
              <tbody>{this.renderSuppliers()}</tbody>
            </table>
          </div>
          <button
            className="btn-type-2 btn-confirm"
            onClick={this.handleNewSupplier}
            style={{ width: "145px" }}
          >
            &#43; Thêm mới
          </button>
          <SupplierModal
            show={this.state.showModal}
            type={this.state.modalType}
            toggleModal={this.toggleDetailModal}
            productDetail={this.state.suppliers[this.state.modalKey]}
            setSupplier={this.setSupplier}
            resetSupplier={this.resetSupplier}
            getSupplierState={this.getSupplierState}
            fetchSupplier={this.fetchSupplier}
          ></SupplierModal>
        </div>
      </>
    );
  }
}

export default SupplierList;
