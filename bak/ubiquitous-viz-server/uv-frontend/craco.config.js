const CracoLessPlugin = require('craco-less')

module.exports = {
  plugins: [
    {
      plugin: CracoLessPlugin,
      options: {
        lessLoaderOptions: {
          lessOptions: {
            modifyVars: {
              '@primary-color': '#89a3a2',
              '@info-color': '#343e4b',
              '@link-color': '#89a3a2',
              '@success-color': '#5eab69',
              '@warning-color': '#dc9b31',
              '@error-color': '#f44336',
              '@font-size-base': '14px',
              '@heading-color': 'rgba(52, 62, 75, 0.85)',
              '@text-color': 'rgba(52, 62, 75, 0.65)',
              '@text-color-secondary': 'rgba(52, 62, 75, 0.45)',
            },
            javascriptEnabled: true,
          },
        },
      },
    },
  ],
}
