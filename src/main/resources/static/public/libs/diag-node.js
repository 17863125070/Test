var diagGraph = {
  //diag图数据操作
  state: [],
  edg: [],
  statePoint: '',
  g: '',
  init: function (statePoint, state, edg) {
    this.statePoint = statePoint
    this.state = state
    this.edg = edg
    this.createG()
    this.renderG()
  },
  createG: function () {
    this.g = new dagreD3.graphlib.Graph()
        .setGraph({rankdir: 'LR'})
        .setDefaultEdgeLabel(function () {
          return {}
        })
  },
  drawNode: function () {
    for (let i in this.state) {
      //画点
      let el = this.state[i]
      let style = ''
      if (el.id === this.statePoint) {
        if (el.class === 'type-suss') {
          style = 'stroke: #35b34a; stroke-width: 1px;'
        } else if (el.class === 'type-fail') {
          style = 'stroke:#f15533; stroke-width: 1px;'
        } else if (el.class === 'type-normal') {
          style = 'stroke:#a5e0ee; stroke-width: 1px;'
        } else if (el.class === 'type-init') {
          style = 'stroke:#a5e0ee; stroke-width: 1px;'
        } else if (el.class === 'type-ready') {
          style = 'stroke:#a5e0ee; stroke-width: 1px;'
        } else if (el.class === 'type-queue') {
          style = 'stroke:#a5e0ee; stroke-width: 1px;'
        } else if (el.class === 'type-run') {
          style = 'stroke:#a5e0ee; stroke-width: 1px;'
        } else if (el.class === 'type-freeze') {
          style = 'stroke:grey; stroke-width: 1px;'
        }
      }
      this.g.setNode(el.id, {
        id: el.id,
        label: el.label,
        class: el.class,
        style: style
      })
    }
    this.g.nodes().forEach(v => {
      //画圆角
      var node = this.g.node(v)
      // Round the corners of the nodes
      node.rx = node.ry = 5
    })
  },
  drawEdg: function () {
    for (let i in this.edg) {
      // 画连线
      let el = this.edg[i]
      if (el.start === this.statePoint || el.end === this.statePoint) {
        this.g.setEdge(el.start, el.end, {
          id: el.id,
          style: 'stroke: #0fb2cc; fill: none;stroke-width: 2px;cursor:pointer;',
          arrowheadStyle: 'fill: #0fb2cc;stroke: #0fb2cc;',
          arrowhead: 'vee',
          label: el.name
        })
      } else {
        this.g.setEdge(el.start, el.end, {
          id: el.id,
          style: 'fill: none;stroke-width: 2px;cursor:pointer;',
          arrowhead: 'vee',
          label: el.name
        })
      }
    }
  },
  renderG: function () {
    var render = new dagreD3.render()
    var svg = d3.select('#svgCanvas')
    svg.select('g').remove() //删除以前的节点
    svg.append('g')
    var inner = svg.select('g')
    /*var zoom = d3.zoom().on('zoom', function () {
      //放大
      inner.attr('transform', d3.event.transform)
    })
    svg.call(zoom)*/
    this.drawNode()
    this.drawEdg()
    render(d3.select('svg g'), this.g) //渲染节点
    svg.select('g').remove()
     return this.g;

    /*var initialScale =
      svg.node().clientWidth/this.g.graph().width > svg.node().clientHeight/this.g.graph().height
        ? svg.node().clientHeight*0.95/this.g.graph().height
        : svg.node().clientWidth*0.95/this.g.graph().width
    console.log("clientWidth"+svg.node().clientWidth);
    console.log("clientHeight"+svg.node().clientHeight);
    console.log("this.g.graph().width"+this.g.graph().width+"this.g.graph().height"+this.g.graph().height);
    var tWidth =
      (svg.node().clientWidth - this.g.graph().width * initialScale) / 2
    var tHeight =
      (svg.node().clientHeight - this.g.graph().height * initialScale) / 2
    console.log("initialScale:"+initialScale+"tWidth:"+tWidth+"tHeight"+tHeight)
    svg.call(
      zoom.transform,
      d3.zoomIdentity.translate(tWidth, tHeight).scale(initialScale)
    ) //元素居中
  },
  changePoint: function(point) {
    console.log(point);
    this.statePoint = point * 1.0
    this.renderG()
  },
  showPoint:function (point) {

    console.log(point);
  }*/
  }
}
