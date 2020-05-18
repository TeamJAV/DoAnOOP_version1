import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { ReactComponent as Logo } from "../style/img/github-brands.svg";

export default class NavigationBar extends Component {
  render() {
    return (
      <Navbar bg="light" expand="lg" className="justify-content-center">
        <div className="mx-auto d-sm-flex d-block flex-sm-nowrap">
          <Navbar.Brand href="#home">
            <Logo style={{ width: 50, height: 50 }}></Logo>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="#home">Nhập hàng</Nav.Link>
              <Nav.Link href="/selling">Bán hàng</Nav.Link>
              <Nav.Link href="#link">Trả hàng</Nav.Link>
              <Nav.Link href="#link">Thống kê</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </div>
      </Navbar>
    );
  }
}
