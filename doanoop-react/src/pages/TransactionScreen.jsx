import React, { Component } from "react";
import { Form, Tabs, Tab } from "react-bootstrap";
import ProfitSum from "../components/statistic/ProfitSum";
import NavigationBar from "../components/NavigationBar";
import InvoiceList from "../components/statistic/InvoiceList";
import "../style/css/transactionScreen.css";
import { convertToISOString, compareDateString } from "../utils/time";

export default class TransactionScreen extends Component {
  today = new Date();
  state = {
    time: "today",
    type: "selling",
    invoice: "",
    specTime: {
      from: convertToISOString(this.today),
      to: convertToISOString(this.today),
    },
  };
  timeOptions = {
    today: "Hôm nay",
    this_week: "Tuần này",
    this_month: "Tháng này",
  };
  typeOptions = {
    selling: "Bán",
    import: "Nhập",
    refund: "Trả",
  };
  componentDidMount() {
    console.log(this.state.specTime);
  }
  // componentDidUpdate(prevProps, prevState) {
  //   if (
  //     prevState.time !== this.state.time ||
  //     prevState.type !== this.state.type
  //   ) {
  //     this.getInvoice();
  //   }
  // }
  handleChangeTime = (event) => {
    const time = event.target.value;
    console.log(time);
    this.setState({
      time: time,
    });
  };

  handleChangeSpecificTime = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    const validDiffBetweenTime =
      name === "from"
        ? compareDateString(value, this.state.specTime.to)
        : compareDateString(this.state.specTime.from, value);

    if (!validDiffBetweenTime) return;
    this.setState(
      (prevState) => ({
        specTime: {
          ...prevState.specTime,
          [name]: convertToISOString(value),
        },
      }),
      () => {
        console.log(this.state.specTime);
      }
    );
  };
  render() {
    return (
      <>
        <NavigationBar pathname="/trans-stats"></NavigationBar>
        <div className="statistic-container">
          <div className="statistic-container__invoice general-container">
            <Form className="statistic-form">
              {["today", "this_week", "this_month"].map((val) => (
                <Form.Check
                  inline
                  label={this.timeOptions[val]}
                  name="time"
                  type="radio"
                  value={val}
                  key={val}
                  defaultChecked={val === "today" ? true : false}
                  onChange={this.handleChangeTime}
                />
              ))}
              <Form.Check inline key="specific">
                <input
                  name="time"
                  type="radio"
                  value="specific"
                  className="form-check-input"
                  onChange={this.handleChangeTime}
                ></input>
                <div>
                  <p className="inline-blck no-mg">Từ: &nbsp;</p>
                  <input
                    type="date"
                    name="from"
                    value={this.state.specTime.from}
                    onChange={this.handleChangeSpecificTime}
                    disabled={this.state.time !== "specific" ? true : false}
                  />
                  <p className="inline-blck no-mg">&nbsp; Đến: &nbsp;</p>
                  <input
                    type="date"
                    name="to"
                    value={this.state.specTime.to}
                    onChange={this.handleChangeSpecificTime}
                    disabled={this.state.time !== "specific" ? true : false}
                  />
                </div>
              </Form.Check>
            </Form>
            <Tabs
              id="controlled-tab"
              activeKey={this.state.type}
              onSelect={(type) => {
                this.setState({ type: type });
              }}
              transition={false}
              unmountOnExit={true}
            >
              {["selling", "import", "refund"].map((val) => (
                <Tab key={val} eventKey={val} title={this.typeOptions[val]}>
                  <InvoiceList
                    type={this.state.type}
                    time={this.state.time}
                    specTime={this.state.specTime}
                  ></InvoiceList>
                </Tab>
              ))}
            </Tabs>
          </div>
          <div className="statistic-container__modules">
            <ProfitSum
              time={this.state.time}
              specTime={this.state.specTime}
            ></ProfitSum>
          </div>
        </div>
      </>
    );
  }
}
