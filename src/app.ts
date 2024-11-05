import express, { Request, Response } from "express"

const app = express();

app.get("", (req: Request, res: Response)=>{
    res.send("How are your eissa? okok")
})

export default app;