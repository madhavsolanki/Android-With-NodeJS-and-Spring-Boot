import express from "express";
import User from "./user.js";
import bcrypt from "bcrypt"
import jwt from "jsonwebtoken";
import mongoose from "mongoose";

mongoose.connect("mongodb://127.0.0.1:27017/api_demo").then(() => console.log("DB Connected Successfully"))

const app = express();

app.use(express.json());

app.post("/signup", async (req, res) => {
    const { username, password } = req.body;
    const user = await User.findOne({ username });
    if (user) {
        return res.json({ message: "User already exists with this username", success: false });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    const newUser = new User({ username, password: hashedPassword });
    await newUser.save();
    res.status(200).json({ message: "User created successfully", success: true });
})

app.post("/login", async (req, res) => {
    const { username, password } = req.body;
    const user = await User.findOne({ username });
    if (!user) {
        return res.status(401).json({ message: "User doesn't exist with this username", success: false });
    }

    const matchPassword = await bcrypt.compare(password, user.password);
    if (!matchPassword) return res.status(401).send('Invalid credentials');

    const token = jwt.sign({ id: user._id }, 'your_jwt_secret', { expiresIn: '1h' });
    res.json({ token });
})

app.listen(3000, () => {
    console.log("Server is running on PORT: 3000");
})