import express, { Request, Response } from "express"

const app = express();

app.get("", (req: Request, res: Response)=>{
    res.send("How are your eissa? okok")
})

app.listen(5000, ()=>{
    console.log("server startokoked")
})