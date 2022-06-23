var path = require('path');

module.exports = {
    entry: './src/main/js/index.js',
    // devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    // watch: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", ["@babel/preset-react", {"runtime":"automatic"}]]
                    }
                }]
            } ,
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"]
            },
            {
                test: /\.(woff|woff2|ttf|eot)$/,
                use: 'file-loader?name=fonts/[name].[ext]!static'
            }   
        ]
    }
};