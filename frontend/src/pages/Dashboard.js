import React, { useEffect, useState } from "react";
import API from "../services/api";

function Dashboard() {

  const [data, setData] = useState([]);

  // 🔥 API CALL
  useEffect(() => {
    API.get("/products")
      .then((res) => {
        console.log("FULL RESPONSE:", res);
        console.log("DATA:", res.data);

        // ✅ safe handling (different backend formats)
        const products = res.data.data || res.data.products || res.data;

        setData(products);
      })
      .catch((err) => {
        console.log("ERROR:", err);
      });
  }, []);

  return (
    <div>
      <h1>Dashboard</h1>

      {/* Total count */}
      <h2>Total Products: {data.length}</h2>

      {/* Empty state */}
      {data.length === 0 ? (
        <p>No products found</p>
      ) : (
        data.map((item) => (
          <div key={item.id} style={{ marginBottom: "10px" }}>
            
            <h3>{item.name}</h3>
            <p>SKU: {item.sku}</p>
            <p>Quantity: {item.quantity}</p>

            {/* barcode image safe check */}
			{(item.barcode_path || item.barcodePath) ? (
			  <img
			    src={
			      "http://localhost:8080/" +
			      (item.barcode_path || item.barcodePath)
			    }
			    alt="barcode"
			    width="150"
			  />
			) : (
			  <p>No barcode</p>
			)}

            <hr />
          </div>
        ))
      )}
    </div>
  );
}

export default Dashboard;