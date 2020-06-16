import React, { Component } from "react";

export default class ProfitSum extends Component {
  state = {
    summary: "",
    isFetching: true,
  };
  componentDidMount() {
    this.setSummary();
  }
  componentDidUpdate(prevProps, prevState) {
    if (prevProps.time !== this.props.time) {
      this.setSummary();
    }
  }
  setSummary = () => {
    fetch(`http://localhost:8081/statistic/money?time=${this.props.time}`, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.setState(
          {
            summary: data,
            isFetching: false,
          },
          () => {
            console.log(this.state);
          }
        );
      })
      .catch((err) => {
        console.log(err);
      });
  };
  render() {
    return (
      <>
        {this.state.isFetching ? (
          <div>Đang tính toán...</div>
        ) : (
          <div>
            <div className="total-collect">
              <div>Tổng thu:</div>
              <div>{this.state.summary[0].TotalCollect}</div>
            </div>
            <div className="total-pay">
              <div>Tổng chi:</div>
              <div>{this.state.summary[0].TotalPay}</div>
            </div>
            <div className="total-interest">
              <div>Tổng lãi:</div>
              <div>{this.state.summary[0].TotalInterest}</div>
            </div>
          </div>
        )}
      </>
    );
  }
}
