import React, { useEffect, useState } from "react";
import API from "../services/api";
import "./Dashboard.css";

function Dashboard() {

  const [data, setData] = useState([]);

  useEffect(() => {

    API.get("/products")
      .then((res) => {

        console.log("FULL RESPONSE:", res);

        const products =
          res.data.data || res.data.products || res.data;

        setData(products);

      })
      .catch((err) => {
        console.log("ERROR:", err);
      });

  }, []);

  return (

    <div className="dashboard">

      <div className="top-bar">
        <h1>Inventory Dashboard</h1>

        <div className="total-box">
          Total Products : {data.length}
        </div>
      </div>

      {data.length === 0 ? (

        <div className="empty">
          No Products Found
        </div>

      ) : (

        <div className="product-grid">

          {data.map((item) => (

            <div className="product-card" key={item.id}>

              <div className="product-header">
                <h2>{item.name}</h2>
              </div>

              <div className="product-body">

                <p>
                  <span>SKU :</span> {item.sku}
                </p>

                <p>
                  <span>Quantity :</span> {item.quantity}
                </p>

                {(item.barcode_path || item.barcodePath) ? (

                  <img
                    src={
                      "http://localhost:8080/" +
                      (item.barcode_path || item.barcodePath)
                    }
                    alt="barcode"
                    className="barcode"
                  />

                ) : (

                  <p className="no-barcode">
                    No Barcode
                  </p>

                )}

              </div>

            </div>

          ))}

        </div>

      )}

    </div>

  );

}

export default Dashboard;